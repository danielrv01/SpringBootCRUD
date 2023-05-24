package com.co.parrolabs.controller.customer;

import com.co.parrolabs.repositories.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private ShippingAddressDTO primaryShippingAddress;

    public static CustomerDTO fromEntity(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPrimaryShippingAddress(ShippingAddressDTO.fromEntity(customer.getShippingAddress()));
        return customerDTO;
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setId(this.getId());
        customer.setName(this.getName());
        customer.setPhone(this.getPhone());
        customer.setEmail(this.getEmail());
        customer.setShippingAddress(this.getPrimaryShippingAddress().toEntity());
        return customer;
    }

}
