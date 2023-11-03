/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.challenge.models.TransactionDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionIntegrationTestBase {

  protected static final String CREATE_TRANSACTION_URL = "/transactions/";
  protected static final String GET_TRANSACTIONS_BY_TYPE_URL = "/transactions/types/";
  protected static final String SAVE_TRANSACTIONS_URL = "/transactions/sum/";

  protected static final String TRANSACTION_NOT_FOUND_ERROR_MESSAGE = "Transaction not found";

  @Autowired protected MockMvc mockMvc;

  @Autowired protected ObjectMapper objectMapper;

  @Autowired protected TransactionService transactionService;

  @BeforeEach
  void setupBeforeEach() {}

  protected void saveTransactionDtos(
      List<Long> transactionDtoIds, List<TransactionDto> transactionDtoList) throws Exception {
    for (int idx = 0; idx < transactionDtoList.size(); idx++) {
      saveTransactionDto(transactionDtoIds.get(idx), transactionDtoList.get(idx));
    }
  }

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
}
