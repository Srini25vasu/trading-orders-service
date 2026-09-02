package com.jusmore.trading.platform.customer.controller;

import com.jusmore.trading.platform.customer.dto.request.CreateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.request.UpdateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.response.CustomerListResponse;
import com.jusmore.trading.platform.customer.dto.response.CustomerResponse;
import com.jusmore.trading.platform.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {
  private final CustomerService customerService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
    return customerService.createCustomer(request);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerResponse getCustomerById(@PathVariable String id) {
    return customerService.getCustomerById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerResponse updateCustomer(@PathVariable String id, @Valid @RequestBody UpdateCustomerRequest request) {
    return customerService.updateCustomer(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCustomer(@PathVariable String id) {
    customerService.deleteCustomer(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CustomerListResponse> getAllCustomers() {
    return customerService.getAllCustomers();
  }
}

