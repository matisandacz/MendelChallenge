/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.mappers;

import com.mendel.challenge.domain.Transaction;
import com.mendel.challenge.models.TransactionDto;
import java.util.ArrayList;

public class TransactionMapper {

  private TransactionMapper() {}

  public static TransactionDto toModel(Transaction transaction) {
    return TransactionDto.builder()
        .amount(transaction.getAmount())
        .type(transaction.getType())
        .parentId(transaction.getParentId())
        .build();
  }

  public static Transaction toDomain(long transactionId, TransactionDto transactionDto) {
    return Transaction.builder()
        .transactionId(transactionId)
        .amount(transactionDto.getAmount())
        .type(transactionDto.getType())
        .childrenTransactions(new ArrayList<>())
        .parentId(transactionDto.getParentId())
        .build();
  }
}
