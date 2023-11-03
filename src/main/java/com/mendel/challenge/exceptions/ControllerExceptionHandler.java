/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorInfo> methodArgumentNotValidHandler(
      MethodArgumentNotValidException ex) {

    Map<String, String> errorMap = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              errorMap.put(error.getField(), error.getDefaultMessage());
            });

    return createErrorResponse(errorMap.toString(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {NoSuchElementException.class})
  public ResponseEntity<ErrorInfo> elementNotFoundHandler(Exception ex) {
    return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<ErrorInfo> defaultExceptionHandler(Exception ex) {
    return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorInfo> createErrorResponse(String errorMessage, HttpStatus status) {
    ErrorInfo errorInfo = new ErrorInfo(errorMessage, status.value());
    return new ResponseEntity<>(errorInfo, status);
  }
}
