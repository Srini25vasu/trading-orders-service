package com.jusmore.trading.platform.customer.service;

import com.jusmore.trading.platform.customer.dto.request.CreateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.request.UpdateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.response.CustomerResponse;

public interface CustomerService {
  CustomerResponse createCustomer(CreateCustomerRequest request);
  CustomerResponse getCustomerById(String id);
  CustomerResponse updateCustomer(String id, UpdateCustomerRequest request);
  void deleteCustomer(String id);
}

