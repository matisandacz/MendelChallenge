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

    // Save the transaction
    transactions.put(transaction.getTransactionId(), transaction);

    if (transaction.getParentId() == null) return;

    // If this transaction is related to a parent, add it as a child.
    Transaction parentTransaction =
        getTransactionOrThrow(
            transaction.getParentId(),
            String.format("Parent transaction with id %d not found", transaction.getParentId()));
    parentTransaction.getChildrenTransactions().add(transaction);
  }

  public List<Long> getTransactionsByType(String type) {
    return transactions.values().stream()
        .filter(transaction -> type.equals(transaction.getType()))
        .map(Transaction::getTransactionId)
        .collect(Collectors.toList());
  }

  public double sumAllRelatedTransactions(Long transactionId) {
    Transaction transaction =
        getTransactionOrThrow(
            transactionId, String.format("Transaction with id %d not found", transactionId));
    return sumAllRelatedTransactionsRecursively(transaction);
  }

  private double sumAllRelatedTransactionsRecursively(Transaction transaction) {

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

  private Transaction getTransactionOrThrow(Long transactionId, String errorMessage) {
    Transaction transaction = transactions.get(transactionId);
    if (transaction == null) {
      throw new NoSuchElementException(errorMessage);
    }
    return transaction;
  }
}
