package com.microservices.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customers first name is required")
        String firstName,
        @NotNull(message = "Customers lastname is required")
        String lastName,
        @NotNull(message = "email is required")
        @Email(message = "not a valid email")
        String email,
        Address address
) {
}
