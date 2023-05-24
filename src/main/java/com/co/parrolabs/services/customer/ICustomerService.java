package com.co.parrolabs.services.customer;

import com.co.parrolabs.controller.customer.CustomerDTO;
import com.co.parrolabs.repositories.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    Optional<Customer> getCustomerById(Long id);

    List<Customer> getAllCustomers();

    Optional<Customer> createCustomer(Customer customer);

    Optional<Customer> updateCustomer(Long id, Customer updatedCustomer);

    boolean deleteCustomer(Long id);

    List<Customer> searchCustomers(String name, String phone, String email);
}
