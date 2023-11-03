/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mendel.challenge.domain.Transaction;
import com.mendel.challenge.models.TransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

public class TransactionIntegrationTest extends TransactionIntegrationTestBase {

  @Test
  void greetingShouldReturnDefaultMessage() throws Exception {
    mockMvc.perform(get("/transactions/types/Cat")).andExpectAll(status().isOk());
  }

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
}
