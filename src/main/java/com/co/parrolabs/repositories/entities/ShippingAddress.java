package com.co.parrolabs.repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "shipping_addresses")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetAndNumber;

    private String city;

    private String state;

    private String zipCode;

    private String country;

}