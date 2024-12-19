package com.microservices.ecommerce.customer;

import com.microservices.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepo;

    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
        var customer = customerRepo.save(customerMapper.customerRequestToCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = customerRepo.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update customer :: No customer fund with the provided ID :: %s", request.id())
                ));
        mergeCustomer(customer, request);
        customerRepo.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())){customer.setFirstName(request.firstName());}

        if (StringUtils.isNotBlank(request.lastName())){customer.setLastName(request.lastName());}

        if (StringUtils.isNotBlank(request.email())){customer.setEmail(request.email());}

        if (request.address() != null){customer.setAddress(request.address());}
    }

    public List<CustomerResponse> fetchAllCustomer() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper::customerToCustomerResponse)
                .collect(Collectors.toList());
    }

    public Boolean findCustomerById(String id) {
        return customerRepo.findById(id)
                .isPresent();
    }

    public CustomerResponse findById(String id) {
        return customerRepo.findById(id)
                .map(customerMapper :: customerToCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Customer not found with provided id :: %s", id)
                ));
    }

    public void deleteCustomerById(String id) {
        if (findCustomerById(id)){
            customerRepo.deleteById(id);
        }
    }
}
