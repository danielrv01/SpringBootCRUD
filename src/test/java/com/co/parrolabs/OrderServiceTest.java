package com.co.parrolabs;

import com.co.parrolabs.controller.order.OrderDTO;
import com.co.parrolabs.repositories.CustomerRepository;
import com.co.parrolabs.repositories.OrderRepository;
import com.co.parrolabs.repositories.ProductRepository;
import com.co.parrolabs.repositories.entities.*;
import com.co.parrolabs.services.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductRepository productRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, customerRepository, productRepository);
    }

    @Test
    public void testAddOrder_WithValidCustomer_ShouldReturnOrderDTO() {
        // Mock data
        ShippingAddress shippingAddress = new ShippingAddress(1L, "123 Street", "medellin", "Antioquia", "12345", "CO");
        Customer customer = new Customer(1L, "John Doe", "1234567890", "john@example.com", shippingAddress);
        Product product = new Product(1L, "Product A", BigDecimal.valueOf(10.0), BigDecimal.valueOf(1.0));
        Order order = new Order(1L, "ORD-001", LocalDate.now(), customer, shippingAddress, PaymentType.CREDIT_CARD, new HashMap<>(), BigDecimal.valueOf(50.0));
        order.getProductsQuantities().put(product, 2);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Perform the test
        Optional<OrderDTO> result = orderService.addOrder(order);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(order.getOrderNumber(), result.get().getOrderNumber());
        assertEquals(order.getTotalOrderValue(), result.get().getTotalOrderValue());
        assertEquals(order.getCustomer().getId(), result.get().getCustomerId());
    }

    @Test
    public void testAddOrder_WithInvalidCustomer_ShouldReturnEmptyOptional() {
        // Mock data
        ShippingAddress shippingAddress = new ShippingAddress(1L, "123 Street", "medellin", "Antioquia", "12345", "CO");
        Customer customer = new Customer(1L, "John Doe", "1234567890", "john@example.com", shippingAddress);
        Order order = new Order(1L, "ORD-001", LocalDate.now(), customer, shippingAddress, PaymentType.CREDIT_CARD, new HashMap<>(), BigDecimal.valueOf(50.0));

        when(customerRepository.findById(order.getCustomer().getId())).thenReturn(Optional.empty());

        // Perform the test
        Optional<OrderDTO> result = orderService.addOrder(order);

        // Assertions
        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteOrder_WithExistingOrderNumber_ShouldReturnTrue() {
        // Mock data
        String orderNumber = "ORD-001";
        ShippingAddress shippingAddress = new ShippingAddress(1L, "123 Street", "medellin", "Antioquia", "12345", "CO");
        Customer customer = new Customer(1L, "John Doe", "1234567890", "john@example.com", shippingAddress);
        Order order = new Order(1L, "ORD-001", LocalDate.now(), customer, shippingAddress, PaymentType.CREDIT_CARD, new HashMap<>(), BigDecimal.valueOf(50.0));


        when(orderRepository.findByOrderNumber(orderNumber)).thenReturn(order);

        // Perform the test
        boolean result = orderService.deleteOrder(orderNumber);

        // Assertions
        assertTrue(result);
        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    @Test
    public void testDeleteOrder_WithNonExistingOrderNumber_ShouldReturnFalse() {
        // Mock data
        String orderNumber = "ORD-001";

        when(orderRepository.findByOrderNumber(orderNumber)).thenReturn(null);

        // Perform the test
        boolean result = orderService.deleteOrder(orderNumber);

        // Assertions
        assertFalse(result);
        verify(orderRepository, never()).deleteById(any());
    }

    @Test
    public void testGetOrderById_WithExistingOrderNumber_ShouldReturnOrder() {
        // Mock data
        String orderNumber = "ORD-001";
        ShippingAddress shippingAddress = new ShippingAddress(1L, "123 Street", "medellin", "Antioquia", "12345", "CO");
        Customer customer = new Customer(1L, "John Doe", "1234567890", "john@example.com", shippingAddress);
        Order order = new Order(1L, "ORD-001", LocalDate.now(), customer, shippingAddress, PaymentType.CREDIT_CARD, new HashMap<>(), BigDecimal.valueOf(50.0));

        when(orderRepository.findByOrderNumber(orderNumber)).thenReturn(order);

        // Perform the test
        Optional<Order> result = orderService.getOrderById(orderNumber);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(order.getOrderNumber(), result.get().getOrderNumber());
        assertEquals(order.getCustomer().getId(), result.get().getCustomer().getId());
    }

    @Test
    public void testGetOrderById_WithNonExistingOrderNumber_ShouldReturnEmptyOptional() {
        // Mock data
        String orderNumber = "ORD-001";

        when(orderRepository.findByOrderNumber(orderNumber)).thenReturn(null);

        // Perform the test
        Optional<Order> result = orderService.getOrderById(orderNumber);

        // Assertions
        assertFalse(result.isPresent());
    }
}
