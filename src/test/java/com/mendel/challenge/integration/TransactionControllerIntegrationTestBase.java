/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.challenge.models.TransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTestBase {

  protected final static String CREATE_TRANSACTION_URL = "/transactions/";
  protected final static String GET_TRANSACTIONS_BY_TYPE_URL = "/transactions/types/";
  protected final static String SAVE_TRANSACTIONS_URL = "/transactions/sum/";

  @Autowired protected MockMvc mockMvc;

  @Autowired protected ObjectMapper objectMapper;

  @BeforeEach
  void setupBeforeEach() {}

  protected void saveTransactionDtos(List<Long> transactionDtoIds, List<TransactionDto> transactionDtoList) throws Exception {
    for (int idx = 0; idx < transactionDtoList.size(); idx++) {
      saveTransactionDto(transactionDtoIds.get(idx), transactionDtoList.get(idx));
    }
  }

  protected void saveTransactionDto(Long transactionId, TransactionDto transactionDto) throws Exception {
    // Save the transaction
    mockMvc
            .perform(
                    MockMvcRequestBuilders.post(CREATE_TRANSACTION_URL + transactionId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(transactionDto))
                            .accept(MediaType.APPLICATION_JSON))
            .andReturn();
  }

}
