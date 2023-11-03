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
    transactions.put(transaction.getTransaction_id(), transaction);

    if (transaction.getParentId() == null) return;

    // If this transaction is related to a parent, add it as a child.
    Transaction parentTransaction = transactions.get(transaction.getParentId());

    if (parentTransaction == null) {
      throw new NoSuchElementException(
          String.format("Parent transaction with id %d not found", transaction.getParentId()));
    }

    parentTransaction.getChildrenTransactions().add(transaction);
  }

  public List<Long> getTransactionsByType(String type) {
    return transactions.values().stream()
        .filter(transaction -> type.equals(transaction.getType()))
        .map(Transaction::getTransaction_id)
        .collect(Collectors.toList());
  }

  public double sumAllRelatedTransactions(Long transaction_id) {
    Transaction transaction = transactions.get(transaction_id);

    if (transaction == null) {
      throw new NoSuchElementException(
          String.format("Transaction with id %d not found", transaction_id));
    }

    return sumAllRelatedTransactionsHelper(transaction);
  }

  private double sumAllRelatedTransactionsHelper(Transaction transaction) {

    // Base Case
    if (transaction.getChildrenTransactions().isEmpty()) {
      return transaction.getAmount();
    }

    Double transactionSum = transaction.getAmount();

    // Recursive Case
    for (Transaction t : transaction.getChildrenTransactions()) {
      transactionSum += sumAllRelatedTransactionsHelper(t);
    }

    return transactionSum;
  }
}
