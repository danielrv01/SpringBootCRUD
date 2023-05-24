package com.co.parrolabs.services.customer;

import com.co.parrolabs.repositories.entities.Customer;
import com.co.parrolabs.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService{

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    @Override
    public Optional<Customer> createCustomer(Customer customer) {
        return Optional.ofNullable(customerRepository.save(customer));
    }
    @Override
    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            updatedCustomer.setId(id);
            return Optional.ofNullable(customerRepository.save(updatedCustomer));
        }
        return Optional.empty();
    }
    @Override
    public boolean deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> searchCustomers(String name, String phone, String email) {
        return customerRepository.searchCustomers(name, phone, email);
    }
}
