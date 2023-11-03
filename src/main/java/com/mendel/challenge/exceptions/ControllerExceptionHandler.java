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

    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorInfo message = new ErrorInfo(errorMap.toString(), status.value());
    return new ResponseEntity<>(message, status);
  }

  @ExceptionHandler(value = {NoSuchElementException.class})
  public ResponseEntity<ErrorInfo> elementNotFoundHandler(Exception ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorInfo message = new ErrorInfo(ex.getMessage(), status.value());
    return new ResponseEntity<>(message, status);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<ErrorInfo> defaultExceptionHandler(Exception ex) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorInfo message = new ErrorInfo(ex.getMessage(), status.value());
    return new ResponseEntity<>(message, status);
  }
}
