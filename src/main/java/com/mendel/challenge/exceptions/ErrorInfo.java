/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.exceptions;

import lombok.Getter;

@Getter
public class ErrorInfo {
  private final String errorMessage;

  private final int statusCode;

  public ErrorInfo(String errorMessage, int statusCode) {
    this.errorMessage = errorMessage;
    this.statusCode = statusCode;
  }
}
