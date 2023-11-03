/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Transaction {

  public Transaction(
      Long transactionId,
      Double amount,
      String type,
      @Nullable Long parentId,
      Set<Transaction> childrenTransactions) {
    this.transactionId = transactionId;
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
    this.childrenTransactions = childrenTransactions;
  }

  public void addChildTransaction(Transaction transaction) {
    childrenTransactions.add(transaction);
  }

  @NotNull private final Long transactionId;

  @NotNull private final Double amount;

  @NotEmpty private final String type;

  @Nullable private final Long parentId;

  @NotNull private final Set<Transaction> childrenTransactions;
}
