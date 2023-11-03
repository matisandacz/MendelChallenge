/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.services;

import com.mendel.challenge.domain.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TransactionService {

  private final HashMap<Long, Transaction> transactions;

  public TransactionService() {
    this.transactions = new HashMap<>();
  }

  public void saveTransaction(Transaction transaction) {

    Transaction parentTransaction;
    if (transaction.getParentId() != null) {
      parentTransaction =
          getTransactionOrThrow(
              transaction.getParentId(),
              String.format("Parent transaction with id %d not found", transaction.getParentId()));
      // If this transaction is related to a parent, add it as a child.
      parentTransaction.getChildrenTransactions().add(transaction);
    }

    // Save the transaction
    transactions.put(transaction.getTransactionId(), transaction);
  }

  public List<Long> getTransactionsByType(String type) {
    return transactions.values().stream()
        .filter(transaction -> type.equals(transaction.getType()))
        .map(Transaction::getTransactionId)
        .collect(Collectors.toList());
  }

  public Double sumAllRelatedTransactions(Long transactionId) {
    Transaction transaction =
        getTransactionOrThrow(
            transactionId, String.format("Transaction with id %d not found", transactionId));
    return sumAllRelatedTransactionsRecursively(transaction);
  }

  private Double sumAllRelatedTransactionsRecursively(Transaction transaction) {

    // Base Case
    if (transaction.getChildrenTransactions().isEmpty()) {
      return transaction.getAmount();
    }

    Double transactionSum = transaction.getAmount();

    // Recursive Case
    for (Transaction t : transaction.getChildrenTransactions()) {
      transactionSum += sumAllRelatedTransactionsRecursively(t);
    }

    return transactionSum;
  }

  protected Transaction getTransactionOrThrow(Long transactionId, String errorMessage) {
    Transaction transaction = transactions.get(transactionId);
    if (transaction == null) {
      throw new NoSuchElementException(errorMessage);
    }
    return transaction;
  }
}
