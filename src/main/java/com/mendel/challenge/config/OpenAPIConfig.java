/*
 *(C) 2023
 *Author: Matias Sandacz
 */
package com.mendel.challenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            contact = @Contact(name = "Matias Sandacz", email = "matiassandacz@gmail.com"),
            description = "OpenApi documentation for the Transaction Controller",
            title = "OpenApi Specification",
            version = "1.0"))
public class OpenAPIConfig {}
