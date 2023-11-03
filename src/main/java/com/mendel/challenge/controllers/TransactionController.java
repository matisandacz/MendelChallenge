/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.controllers;

import com.mendel.challenge.domain.Transaction;
import com.mendel.challenge.mappers.TransactionMapper;
import com.mendel.challenge.models.TransactionDto;
import com.mendel.challenge.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "")
@Tag(name = "Transactions")
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Operation(
      description = "PUT endpoint for creating a new transaction",
      summary = "Creates a new transaction")
  @PutMapping("/transactions/{transaction_id}")
  public ResponseEntity<TransactionDto> createTransaction(
      @PathVariable @NotNull Long transaction_id,
      @RequestBody @Valid TransactionDto transactionDto) {
    Transaction createdTransaction = TransactionMapper.toDomain(transaction_id, transactionDto);
    transactionService.saveTransaction(createdTransaction);
    return new ResponseEntity<>(TransactionMapper.toModel(createdTransaction), HttpStatus.CREATED);
  }

  @Operation(
      description = "GET endpoint for retrieving all transactions based on their type.",
      summary = "Gets all transactions of a type.")
  @GetMapping(value = "/transactions/types/{type}")
  public ResponseEntity<List<Long>> getTransactionsByType(@PathVariable String type) {
    return ResponseEntity.ok(transactionService.getTransactionsByType(type));
  }

  @Operation(
      description = "GET endpoint for calculating the sum of all related transactions.",
      summary = "Calculates the sum of all related transactions.")
  @GetMapping(value = "/transactions/sum/{transaction_id}")
  public ResponseEntity<Double> sumAllRelatedTransactions(
      @PathVariable @NotNull Long transaction_id) {
    return ResponseEntity.ok(transactionService.sumAllRelatedTransactions(transaction_id));
  }
}
