package com.co.parrolabs.controller.customer;

import com.co.parrolabs.repositories.entities.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressDTO implements Serializable {
    private Long id;
    private String streetAndNumber;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    // Constructors, getters, and setters

    // ...

    public static ShippingAddressDTO fromEntity(ShippingAddress shippingAddress) {
        ShippingAddressDTO shippingAddressDTO = new ShippingAddressDTO();
        shippingAddressDTO.setId(shippingAddress.getId());
        shippingAddressDTO.setStreetAndNumber(shippingAddress.getStreetAndNumber());
        shippingAddressDTO.setCity(shippingAddress.getCity());
        shippingAddressDTO.setState(shippingAddress.getState());
        shippingAddressDTO.setZipCode(shippingAddress.getZipCode());
        shippingAddressDTO.setCountry(shippingAddress.getCountry());
        return shippingAddressDTO;
    }

    public ShippingAddress toEntity() {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(this.getId());
        shippingAddress.setStreetAndNumber(this.getStreetAndNumber());
        shippingAddress.setCity(this.getCity());
        shippingAddress.setState(this.getState());
        shippingAddress.setZipCode(this.getZipCode());
        shippingAddress.setCountry(this.getCountry());
        return shippingAddress;
    }
}


