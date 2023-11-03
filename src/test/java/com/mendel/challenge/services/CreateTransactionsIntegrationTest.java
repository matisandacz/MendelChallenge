/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mendel.challenge.domain.Transaction;
import com.mendel.challenge.exceptions.ErrorInfo;
import com.mendel.challenge.models.TransactionDto;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

public class CreateTransactionsIntegrationTest extends TransactionIntegrationTestBase {

  @Test
  void whenSaveValidTransactionThenSuccess() throws Exception {

    Long transactionId = 1L;
    TransactionDto transactionDto =
        TransactionDto.builder().type("CAR").amount(100.0).parentId(null).build();

    // Save Transaction
    MvcResult mvcResult = saveTransactionDto(transactionId, transactionDto);

    // Transaction was saved successfully
    Transaction savedTransaction =
        transactionService.getTransactionOrThrow(
            transactionId, TRANSACTION_NOT_FOUND_ERROR_MESSAGE);
    assertThat(savedTransaction).isNotNull();

    // Status is ok
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

    // Response is ok
    String responseBody = mvcResult.getResponse().getContentAsString();
    TransactionDto response = objectMapper.readValue(responseBody, TransactionDto.class);
    assertThat(response.equals(transactionDto)).isTrue();
  }

  @Test
  void whenSaveTransactionWithNonValidAmountThenError() throws Exception {

    Long transactionId = 2L;
    TransactionDto transactionDto =
        TransactionDto.builder().type("CAR").amount(0.0).parentId(null).build();

    // Save Transaction
    MvcResult mvcResult = saveTransactionDto(transactionId, transactionDto);

    // Status is error
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    // Response is error
    String responseBody = mvcResult.getResponse().getContentAsString();
    ErrorInfo errorInfo = objectMapper.readValue(responseBody, ErrorInfo.class);
    assertThat(errorInfo.getErrorMessage()).isEqualTo("{amount=must be greater than 0}");
    assertThat(errorInfo.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    // Transaction not saved
    assertThrows(
        NoSuchElementException.class,
        () -> {
          transactionService.getTransactionOrThrow(
              transactionId, TRANSACTION_NOT_FOUND_ERROR_MESSAGE);
        });
  }

  @Test
  void whenSaveTransactionWithNonExistentParentThenError() throws Exception {

    Long transactionId = 3L;
    Long parentId = 100L;
    TransactionDto transactionDto =
        TransactionDto.builder().type("CAR").amount(100.0).parentId(parentId).build();

    // Save Transaction
    MvcResult mvcResult = saveTransactionDto(transactionId, transactionDto);

    // Status is error
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    // Response is error
    String responseBody = mvcResult.getResponse().getContentAsString();
    ErrorInfo errorInfo = objectMapper.readValue(responseBody, ErrorInfo.class);
    assertThat(errorInfo.getErrorMessage())
        .isEqualTo(String.format("Parent transaction with id %d not found", parentId));
    assertThat(errorInfo.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());

    // Transaction not saved
    assertThrows(
        NoSuchElementException.class,
        () -> {
          transactionService.getTransactionOrThrow(
              transactionId, TRANSACTION_NOT_FOUND_ERROR_MESSAGE);
        });
  }

  @Test
  void whenSaveTransactionWithValidParentThenSuccess() throws Exception {

    Long transactionIdParent = 4L;
    TransactionDto transactionDtoParent =
        TransactionDto.builder().type("CAR").amount(100.0).parentId(null).build();

    Long transactionIdChild = 5L;
    TransactionDto transactionDtoChild =
        TransactionDto.builder().type("CAR").amount(100.0).parentId(transactionIdParent).build();

    // Save Parent Transaction
    MvcResult mvcResult = saveTransactionDto(transactionIdParent, transactionDtoParent);

    // Save Child Transaction
    MvcResult mvcResultChild = saveTransactionDto(transactionIdChild, transactionDtoChild);

    // Parent Transaction was saved successfully
    Transaction savedTransaction =
        transactionService.getTransactionOrThrow(
            transactionIdParent, TRANSACTION_NOT_FOUND_ERROR_MESSAGE);
    assertThat(savedTransaction).isNotNull();

    // Child Transaction was saved successfully
    Transaction savedTransactionChild =
        transactionService.getTransactionOrThrow(
            transactionIdChild, TRANSACTION_NOT_FOUND_ERROR_MESSAGE);
    assertThat(savedTransactionChild).isNotNull();

    // Parent Status is ok
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

    // Child Status is ok
    assertThat(mvcResultChild.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

    // Parent Response is ok
    String responseBody = mvcResult.getResponse().getContentAsString();
    TransactionDto response = objectMapper.readValue(responseBody, TransactionDto.class);
    assertThat(response.equals(transactionDtoParent)).isTrue();

    // Child Response is ok
    String responseBodyChild = mvcResultChild.getResponse().getContentAsString();
    TransactionDto responseChild = objectMapper.readValue(responseBodyChild, TransactionDto.class);
    assertThat(responseChild.equals(transactionDtoChild)).isTrue();

    // Child has been added to parent
    assertThat(savedTransaction.getChildrenTransactions().contains(savedTransactionChild)).isTrue();
  }
}
