/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class TransactionDto {

  public TransactionDto(Double amount, String type, @Nullable Long parentId) {
    this.amount = amount;
    this.type = type;
    this.parentId = parentId;
  }

  @Positive @NotNull private final Double amount;

  @NotEmpty(message = "type can't be blank")
  private final String type;

  @JsonProperty("parent_id")
  @Nullable private final Long parentId;
}
