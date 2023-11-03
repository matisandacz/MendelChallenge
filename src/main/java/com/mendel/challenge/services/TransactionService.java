/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.services;

import com.mendel.challenge.domain.Transaction;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TransactionService {

  public void saveTransaction(Transaction transaction) {}

  public List<Long> getTransactionsByType(String type) {
    return List.of(1L, 2L, 3L);
  }

  public double sumAllRelatedTransactions(Long transaction_id) {
    return 123.0;
  }
}
