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
      Long transaction_id,
      Double amount,
      String type,
      @Nullable Long parentId,
      List<Long> childrenIds) {
    this.transaction_id = transaction_id;
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
    this.childrenIds = childrenIds;
  }

  @NotNull private final Long transaction_id;

  @NotNull private final Double amount;

  @NotEmpty private final String type;

  @Nullable private final Long parentId;

  private final List<Long> childrenIds;
}
