package com.co.parrolabs.controller.customer;

import com.co.parrolabs.repositories.entities.Customer;
import com.co.parrolabs.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(c -> ResponseEntity.ok(CustomerDTO.fromEntity(c))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream().map(CustomerDTO::fromEntity).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerDTO.toEntity();
        Optional<Customer> createdCustomer = customerService.createCustomer(customer);
        return createdCustomer.map(c -> ResponseEntity.created(URI.create("/customers/" + c.getId())).body(CustomerDTO.fromEntity(c)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO updatedCustomerDTO) {
        Customer updatedCustomer = updatedCustomerDTO.toEntity();
        Optional<Customer> updatedCustomerOpt = customerService.updateCustomer(id, updatedCustomer);
        return updatedCustomerOpt.map(c -> ResponseEntity.ok(CustomerDTO.fromEntity(c))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        boolean deleted = customerService.deleteCustomer(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String phone,
                                                             @RequestParam(required = false) String email) {
        List<Customer> customers = customerService.searchCustomers(name, phone, email);
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(CustomerDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
    }
}

