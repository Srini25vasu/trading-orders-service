package com.jusmore.trading.platform.customer.service.impl;

import com.jusmore.trading.platform.common.exception.ResourceCreationException;
import com.jusmore.trading.platform.common.exception.ResourceNotFoundException;
import com.jusmore.trading.platform.customer.domain.Customer;
import com.jusmore.trading.platform.customer.dto.request.CreateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.request.UpdateCustomerRequest;
import com.jusmore.trading.platform.customer.dto.response.CustomerListResponse;
import com.jusmore.trading.platform.customer.dto.response.CustomerResponse;
import com.jusmore.trading.platform.customer.mapper.CustomerMapper;
import com.jusmore.trading.platform.customer.repository.CustomerRepository;
import com.jusmore.trading.platform.customer.service.CustomerService;
import com.jusmore.trading.platform.shared_kernel.CustomerId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  @Override
  public CustomerResponse createCustomer(CreateCustomerRequest request) {
    log.debug("Erstellung des Kunden: {}", customerMapper.toDto(request));
    try {
      Customer customer = customerMapper.toCustomer(request);
      if (customer.getId() == null) {
        customer.setId(CustomerId.create());
      }
      customerRepository.save(customer);
      log.info("Kunde erstellt mit id: {}", customer.getId());
      return customerMapper.toCustomerResponse(customer);
    } catch (DataAccessException e) {
      log.error("Fehler beim Erstellen des Kunden: {}", request, e);
      throw new ResourceCreationException("Kunde konnte aufgrund eines Datenpersistenzfehlers nicht erstellt werden", e);
    }
  }

  @Override
  public CustomerResponse getCustomerById(String id) {
    //String maskedEmail = maskEmail(email);
    log.info("Abrufen des Kunden mit id: {}", id);
    try {
      Customer customer = customerRepository.findById(CustomerId.create(id))
            .orElseThrow(
                  () -> {
                    String msg = "Kunde nicht gefunden mit id: " + id;
                    log.warn(msg);
                    return new ResourceNotFoundException(msg);
                  });
      log.info("Kunde gefunden mit id: {}", customer.getId());
      return customerMapper.toCustomerResponse(customer);
    } catch (DataAccessException e) {
      log.error("Fehler beim Abrufen des Kunden mit id: {}", id, e);
      throw new ResourceCreationException("Kunde konnte aufgrund eines Datenpersistenzfehlers nicht abgerufen werden", e);
    }
  }


  @Override
  @Transactional
  public CustomerResponse updateCustomer(String id, UpdateCustomerRequest request) {
    log.info("Aktualisierung des Kunden mit id: {}", id);
    try {
      Customer existingCustomer = customerRepository.findById(CustomerId.create(id))
            .orElseThrow(() -> {
              String msg = "Kunde nicht gefunden mit id: " + id;
              log.warn(msg);
              return new ResourceNotFoundException(msg);
            });
      customerMapper.updateCustomerFromRequest(request, existingCustomer);
      Customer customer = customerRepository.save(existingCustomer);
      return customerMapper.toCustomerResponse(customer);
    } catch (DataAccessException e) {
      log.error("Fehler beim Aktualisieren des Kunden mit id: {}", id, e);
      throw new ResourceCreationException("Kunde konnte aufgrund eines Datenpersistenzfehlers nicht aktualisiert werden", e);
    }
  }

  @Override
  @Transactional
  public void deleteCustomer(String id) {
    log.info("Löschen des Kunden mit id: {}", id);
    CustomerId customerId = CustomerId.create(id);
    try {
      if (!customerRepository.existsById(customerId)) {
        String msg = "Kunde nicht gefunden mit id: " + id;
        log.warn(msg);
        throw new ResourceNotFoundException(msg);
      }
      customerRepository.deleteById(customerId);
      log.info("Kunde erfolgreich gelöscht mit id: {}", id);
    } catch (DataAccessException e) {
      log.error("Fehler beim Löschen des Kunden mit id: {}", id, e);
      throw new ResourceCreationException("Kunde konnte aufgrund eines Datenpersistenzfehlers nicht gelöscht werden", e);
    }
  }

  @Override
  public List<CustomerResponse> getAllCustomers() {
    log.info("Abrufen aller Kunden");
    List<Customer> customers = customerRepository.findAllWithTradingOrders();
    return customers.stream()
          .map(customerMapper::toCustomerResponse)
          .toList();
  }

  @Override
  public Page<CustomerListResponse> getCustomersPaginated(Pageable page) {
    log.info("Abrufen aller Kunden mit pagination für Page: {}", page.getPageNumber());
    try {
      return customerRepository.findAll(page)
            .map(customerMapper::toCustomerListResponse);
    } catch (DataAccessException e) {
      log.error("Fehler beim Abrufen der Kunden mit pagination für Page: {}", page.getPageNumber(), e);
      throw new ResourceCreationException("Kunden konnten aufgrund eines Datenpersistenzfehlers nicht abgerufen werden", e);
    }
  }
}
