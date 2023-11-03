/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.challenge.models.TransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionIntegrationTestBase {

  protected static final String CREATE_TRANSACTION_URL = "/transactions/";
  protected static final String GET_TRANSACTIONS_BY_TYPE_URL = "/transactions/types/";
  protected static final String SUM_TRANSACTIONS_URL = "/transactions/sum/";

  protected static final String TRANSACTION_NOT_FOUND_ERROR_MESSAGE = "Transaction not found";

  @Autowired protected MockMvc mockMvc;

  @Autowired protected ObjectMapper objectMapper;

  @Autowired protected TransactionService transactionService;

  @BeforeEach
  void setupBeforeEach() {}

  protected MvcResult saveTransactionDto(Long transactionId, TransactionDto transactionDto)
      throws Exception {
    // Save the transaction
    return mockMvc
        .perform(
            MockMvcRequestBuilders.put(CREATE_TRANSACTION_URL + transactionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDto))
                .accept(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  protected MvcResult getTransactionByType(String type) throws Exception {
    // Save the transaction
    return mockMvc
        .perform(
            MockMvcRequestBuilders.get(GET_TRANSACTIONS_BY_TYPE_URL + type)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  protected MvcResult sumTransactions(Long transactionId) throws Exception {
    // Save the transaction
    return mockMvc
        .perform(
            MockMvcRequestBuilders.get(SUM_TRANSACTIONS_URL + transactionId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andReturn();
  }
}
