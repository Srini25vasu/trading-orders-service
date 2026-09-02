package com.jusmore.trading.platform.customer.service;

import com.jusmore.trading.platform.customer.dto.request.CreateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.request.UpdateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.response.CustomerListResponse;
import com.jusmore.trading.platform.customer.dto.response.CustomerResponse;

import java.util.List;

/**
 * Customer service Schnittstelle.
 * Die Schnittstelle für die Customer Service definiert die Methoden, die für die Verwaltung von Kunden verwendet werden.
 *
 * @author Srini
 * @version 1.0
 * @since 2026-09-02
 */

public interface CustomerService {
  CustomerResponse createCustomer(CreateCustomerRequest request);
  CustomerResponse getCustomerById(String id);
  CustomerResponse updateCustomer(String id, UpdateCustomerRequest request);
  void deleteCustomer(String id);
  List<CustomerListResponse> getAllCustomers();
}

