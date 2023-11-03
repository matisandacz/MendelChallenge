/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

public class TransactionControllerIntegrationTest extends TransactionControllerIntegrationTestBase {

  @Test
  void greetingShouldReturnDefaultMessage() throws Exception {
    mockMvc.perform(get("/transactions/types/Cat")).andExpectAll(status().isOk());
  }
}
