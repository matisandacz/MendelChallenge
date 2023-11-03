/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.models;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionDto {

  public TransactionDto(Double amount, String type, @Nullable Long parentId) {
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
  }

  @Positive @NotNull private final Double amount;

  @NotEmpty(message = "type can't be blank")
  private final String type;

  @Nullable private final Long parentId;
}
