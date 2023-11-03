/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.controllers;

import com.mendel.challenge.domain.Transaction;
import com.mendel.challenge.mappers.TransactionMapper;
import com.mendel.challenge.models.TransactionDto;
import com.mendel.challenge.services.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "")
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PutMapping("/transactions/{transaction_id}")
  public ResponseEntity<TransactionDto> createTransaction(
      @PathVariable long transaction_id, @RequestBody @Valid TransactionDto transactionDto) {
    Transaction createdTransaction = TransactionMapper.toDomain(transaction_id, transactionDto);
    transactionService.saveTransaction(createdTransaction);
    return new ResponseEntity<>(TransactionMapper.toModel(createdTransaction), HttpStatus.CREATED);
  }

  @GetMapping(value = "/transactions/types/{type}")
  public ResponseEntity<List<Long>> getTransactionsByType(@PathVariable String type) {
    return ResponseEntity.ok(transactionService.getTransactionsByType(type));
  }

  @GetMapping(value = "/transactions/sum/{transaction_id}")
  public ResponseEntity<Double> sumAllTransitiveTransactions(@PathVariable long transaction_id) {
    return ResponseEntity.ok(transactionService.sumAllTransitiveTransactions(transaction_id));
  }
}
