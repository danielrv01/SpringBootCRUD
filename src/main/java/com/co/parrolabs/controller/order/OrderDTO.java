package com.co.parrolabs.controller.order;

import com.co.parrolabs.controller.product.ProductDTO;
import com.co.parrolabs.repositories.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {
    private String orderNumber;
    private LocalDate date;
    private Long customerId;
    private Long shippingAddressId;
    private PaymentType paymentType;
    private Map<Long, Integer> productsQuantities;
    private BigDecimal totalOrderValue;


    public Order toEntity() {
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setDate(date);

        Customer customer = new Customer();
        customer.setId(customerId);
        order.setCustomer(customer);

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(shippingAddressId);
        order.setShippingAddress(shippingAddress);

        order.setPaymentType(paymentType);
        order.setTotalOrderValue(totalOrderValue);

        Map<Product, Integer> entityProductsQuantities = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : productsQuantities.entrySet()) {
            Long key = entry.getKey();
            Product product = new Product();
            product.setId(key);
            entityProductsQuantities.put(product, entry.getValue());
        }
        order.setProductsQuantities(entityProductsQuantities);

        return order;
    }

    public static OrderDTO fromEntity(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setDate(order.getDate());

        Optional<Customer> customer = Optional.ofNullable(order.getCustomer());
        customer.ifPresent(value -> orderDTO.setCustomerId(value.getId()));

        Optional<ShippingAddress> shippingAddress = Optional.ofNullable(order.getShippingAddress());
        shippingAddress.ifPresent(value -> orderDTO.setShippingAddressId(value.getId()));

        orderDTO.setPaymentType(order.getPaymentType());
        orderDTO.setTotalOrderValue(order.getTotalOrderValue());

        Map<Long, Integer> dtoProductsQuantities = new HashMap<>();
        for (Map.Entry<Product, Integer> entry : order.getProductsQuantities().entrySet()) {
            Product product = entry.getKey();
            ProductDTO productDTO = ProductDTO.fromEntity(product);
            dtoProductsQuantities.put(productDTO.getId(), entry.getValue());
        }
        orderDTO.setProductsQuantities(dtoProductsQuantities);

        return orderDTO;
    }
}
