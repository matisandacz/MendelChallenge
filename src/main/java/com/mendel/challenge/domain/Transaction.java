/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.domain;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Transaction {

  public Transaction(
      long transaction_id,
      double amount,
      String type,
      @Nullable Long parentId,
      List<Long> childrenIds) {
    this.transaction_id = transaction_id;
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
    this.childrenIds = childrenIds;
  }

  private final long transaction_id;

  private final double amount;

  private final String type;

  @Nullable private final Long parentId;

  private final List<Long> childrenIds;
}
