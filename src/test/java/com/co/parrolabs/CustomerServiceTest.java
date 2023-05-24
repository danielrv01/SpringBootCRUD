package com.co.parrolabs;
import com.co.parrolabs.repositories.CustomerRepository;
import com.co.parrolabs.repositories.entities.Customer;
import com.co.parrolabs.repositories.entities.ShippingAddress;
import com.co.parrolabs.services.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void testGetCustomerById() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "John Doe", String.valueOf(123456789), "test@gmail.com", new ShippingAddress());
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> result = customerService.getCustomerById(customerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer(1L, "John Doe", String.valueOf(123456789), "test@gmail.com", new ShippingAddress()),
                new Customer(2L, "John Doe", String.valueOf(123456789), "test@gmail.com", new ShippingAddress())
                );
        Mockito.when(customerRepository.findAll()).thenReturn(expectedCustomers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertEquals(expectedCustomers.size(), result.size());
        assertTrue(result.containsAll(expectedCustomers));
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        Customer customer = new Customer(1L, "John Doe", String.valueOf(123456789), "test@gmail.com", new ShippingAddress());
        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Optional<Customer> result = customerService.createCustomer(customer);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    public void testUpdateCustomer() {
        // Arrange
        Long customerId = 1L;
        Customer existingCustomer = new Customer(customerId, "John Doe", String.valueOf(123456789), "test@gmail.com", new ShippingAddress());
        Customer updatedCustomer = new Customer(customerId, "Jane Smith", String.valueOf(123456789), "test@gmail.com", new ShippingAddress());

        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        // Act
        Optional<Customer> result = customerService.updateCustomer(customerId, updatedCustomer);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedCustomer, result.get());
    }

    @Test
    public void testDeleteCustomer() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "John Doe", String.valueOf(123456789), "test@gmail.com", new ShippingAddress());

        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        boolean result = customerService.deleteCustomer(customerId);

        // Assert
        assertTrue(result);
        Mockito.verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testSearchCustomers() {
        // Arrange
        String name = "John";
        String phone = "123456789";
        String email = "john@example.com";
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer(1L, "John Doe", String.valueOf(123456789), "test@gmail.com", new ShippingAddress()),
                new Customer(2L, "John Doe", String.valueOf(123456789), "test@gmail.com", new ShippingAddress())
        );
        Mockito.when(customerRepository.searchCustomers(name, phone, email)).thenReturn(expectedCustomers);

        // Act
        List<Customer> result = customerService.searchCustomers(name, phone, email);

        // Assert
        assertEquals(expectedCustomers.size(), result.size());
        assertTrue(result.containsAll(expectedCustomers));
    }
}
