/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.mappers;

import com.mendel.challenge.domain.Transaction;
import com.mendel.challenge.models.TransactionDto;
import java.util.ArrayList;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionMapper {

  public static TransactionDto toModel(Transaction transaction) {
    return TransactionDto.builder()
        .amount(transaction.getAmount())
        .type(transaction.getType())
        .parentId(transaction.getParentId())
        .build();
  }

  public static Transaction toDomain(long transaction_id, TransactionDto transactionDto) {
    return Transaction.builder()
        .transaction_id(transaction_id)
        .amount(transactionDto.getAmount())
        .type(transactionDto.getType())
        .childrenIds(new ArrayList<>())
        .build();
  }
}
