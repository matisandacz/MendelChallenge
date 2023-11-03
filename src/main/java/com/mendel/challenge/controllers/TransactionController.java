/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.controllers;

import com.mendel.challenge.domain.Transaction;
import com.mendel.challenge.mappers.TransactionMapper;
import com.mendel.challenge.models.TransactionDto;
import com.mendel.challenge.services.TransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v0")
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PutMapping("/transactions/{transaction_id}")
  public TransactionDto createTransaction(
      @PathVariable long transaction_id, @RequestBody TransactionDto transactionDto) {
    Transaction createdTransaction = TransactionMapper.toDomain(transaction_id, transactionDto);
    transactionService.storeTransaction(createdTransaction);
    return TransactionMapper.toModel(createdTransaction);
  }

  @GetMapping(value = "/transactions/types/{type}")
  public List<Long> getTransactionsByType(@PathVariable String type) {
    return transactionService.getTransactionsByType(type);
  }

  @GetMapping(value = "/transactions/sum/{transaction_id}")
  public double sumAllTransitiveTransactions(@PathVariable long transaction_id) {
    return transactionService.sumAllTransitiveTransactions(transaction_id);
  }
}
