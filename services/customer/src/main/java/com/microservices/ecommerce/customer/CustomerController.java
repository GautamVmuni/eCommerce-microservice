package com.microservices.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
        return ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> fetchAllCustomer(){
        return ResponseEntity.ok(service.fetchAllCustomer());
    }

    @GetMapping("exists/{customer-id}")
    public ResponseEntity<Boolean> findCustomer(
            @PathVariable("customer-id") String id
    ){
        return ResponseEntity.ok(service.findCustomerById(id));
    }

    @GetMapping("find/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(
            @PathVariable("customer-id") String id
    ){
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("remove/{customer-id}")
    public ResponseEntity<Void> deleteCustomerById(
            @PathVariable("customer-id") String id
    ){
        service.deleteCustomerById(id);
        return ResponseEntity.accepted().build();
    }
}
