/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
  @Override
  public void run(String... args) throws Exception {
    System.out.println("Hello World!");
  }
}
