/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mendel.challenge.models.TransactionDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

public class GetTransactionsByTypeIntegrationTest extends TransactionIntegrationTestBase {

  @Test
  void whenNoTransactionsThenEmptyList() throws Exception {

    MvcResult mvcResult = getTransactionByType("CAR");

    // Status is ok
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    // Response is ok
    String responseBody = mvcResult.getResponse().getContentAsString();
    TypeReference<List<Long>> typeReference = new TypeReference<>() {};
    List<Long> response = objectMapper.readValue(responseBody, typeReference);
    assertThat(response).isEmpty();
  }

  @Test
  void whenTransactionsWithOnlyMatchingTypeThenReturnNonEmptyList() throws Exception {

    Long transactionId = 1L;
    TransactionDto transactionDto =
        TransactionDto.builder().type("CAR").amount(100.0).parentId(null).build();

    // Save Transaction
    saveTransactionDto(transactionId, transactionDto);

    MvcResult mvcResult = getTransactionByType("CAR");

    // Status is ok
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    // Response is ok
    String responseBody = mvcResult.getResponse().getContentAsString();
    TypeReference<List<Long>> typeReference = new TypeReference<>() {};
    List<Long> response = objectMapper.readValue(responseBody, typeReference);
    assertThat(response).isNotEmpty();
    assertThat(response).contains(transactionId);
  }

  @Test
  void whenTransactionsWithMatchingAndNonMatchingTypesThenReturnOnlyMatchingTypes()
      throws Exception {

    Long transactionId = 1L;
    TransactionDto transactionDto =
        TransactionDto.builder().type("CAR").amount(100.0).parentId(null).build();

    Long transactionId2 = 2L;
    TransactionDto transactionDto2 =
        TransactionDto.builder().type("BUS").amount(100.0).parentId(null).build();

    // Save Transaction
    saveTransactionDto(transactionId, transactionDto);
    saveTransactionDto(transactionId2, transactionDto2);

    MvcResult mvcResult = getTransactionByType("CAR");

    // Status is ok
    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    // Response is ok
    String responseBody = mvcResult.getResponse().getContentAsString();
    TypeReference<List<Long>> typeReference = new TypeReference<>() {};
    List<Long> response = objectMapper.readValue(responseBody, typeReference);
    assertThat(response).isNotEmpty();
    assertThat(response).contains(transactionId);
    assertThat(response).doesNotContain(transactionId2);
  }
}
