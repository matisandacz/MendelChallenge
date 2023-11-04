/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.mendel.challenge.exceptions.ErrorInfo;
import com.mendel.challenge.models.TransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

public class SumTransactionsIntegrationTest extends TransactionIntegrationTestBase {

  @Test
  void whenSumTransactionsOfNonExistentIdThenError() throws Exception {

    Long transactionId = 1L;

    MvcResult mvcResult = sumTransactions(transactionId);

    // Status is error
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    // Response is error
    String responseBody = mvcResult.getResponse().getContentAsString();
    ErrorInfo errorInfo = objectMapper.readValue(responseBody, ErrorInfo.class);
    assertThat(errorInfo.getErrorMessage())
        .isEqualTo(String.format("Transaction with id %d not found", transactionId));
    assertThat(errorInfo.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void whenSumTransactionsWithOnlyOneChildInFirstLevelThenSuccess() throws Exception {

    Long transactionId = 1L;
    TransactionDto transactionDto =
        TransactionDto.builder().type("CAR").amount(100.0).parentId(null).build();

    Long transactionIdChild = 2L;
    TransactionDto transactionDtoChild =
        TransactionDto.builder().type("BUS").amount(100.0).parentId(transactionId).build();

    // Save Transactions
    saveTransactionDto(transactionId, transactionDto);
    saveTransactionDto(transactionIdChild, transactionDtoChild);

    // Sum transactions
    MvcResult mvcResult = sumTransactions(transactionId);

    // Status is success
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    // Response is success
    String responseBody = mvcResult.getResponse().getContentAsString();
    Double transactionSum = objectMapper.readValue(responseBody, Double.class);

    // Result is correct
    assertThat(transactionSum).isEqualTo(200.0);
  }

  @Test
  void whenSumTransactionsWithTransitiveChildThenSuccess() throws Exception {

    Long transactionId = 1L;
    TransactionDto transactionDto =
        TransactionDto.builder().type("CAR").amount(100.0).parentId(null).build();

    Long transactionIdChild = 2L;
    TransactionDto transactionDtoChild =
        TransactionDto.builder().type("BUS").amount(100.0).parentId(transactionId).build();

    Long transactionIdChild2 = 3L;
    TransactionDto transactionDtoChild2 =
        TransactionDto.builder().type("BUS").amount(100.0).parentId(transactionIdChild).build();

    Long transactionIdChild3 = 3L;
    TransactionDto transactionDtoChild3 =
        TransactionDto.builder().type("BUS").amount(100.0).parentId(transactionIdChild2).build();

    // Save Transactions
    saveTransactionDto(transactionId, transactionDto);
    saveTransactionDto(transactionIdChild, transactionDtoChild);
    saveTransactionDto(transactionIdChild2, transactionDtoChild2);
    saveTransactionDto(transactionIdChild3, transactionDtoChild3);

    // Sum transactions
    MvcResult mvcResult = sumTransactions(transactionId);

    // Status is success
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    // Response is success
    String responseBody = mvcResult.getResponse().getContentAsString();
    Double transactionSum = objectMapper.readValue(responseBody, Double.class);

    // Result is correct
    assertThat(transactionSum).isEqualTo(400.0);
  }
}
