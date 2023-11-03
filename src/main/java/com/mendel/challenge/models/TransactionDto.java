/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.models;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionDto {

  public TransactionDto(double amount, String type, @Nullable Long parentId) {
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
  }

  @Positive private final double amount;

  @NotEmpty(message = "type can't be blank")
  private final String type;

  @Nullable private final Long parentId;
}
