/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Transaction {

  public Transaction(
      Long transactionId,
      Double amount,
      String type,
      @Nullable Long parentId,
      List<Transaction> childrenTransactions) {
    this.transactionId = transactionId;
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
    this.childrenTransactions = childrenTransactions;
  }

  @NotNull private final Long transactionId;

  @NotNull private final Double amount;

  @NotEmpty private final String type;

  @Nullable private final Long parentId;

  private final List<Transaction> childrenTransactions;
}
