package com.co.parrolabs.services.order;

import com.co.parrolabs.controller.order.OrderDTO;
import com.co.parrolabs.repositories.CustomerRepository;
import com.co.parrolabs.repositories.ProductRepository;
import com.co.parrolabs.repositories.entities.Customer;
import com.co.parrolabs.repositories.entities.Order;
import com.co.parrolabs.repositories.OrderRepository;
import com.co.parrolabs.repositories.entities.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;


@Service
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<OrderDTO> addOrder(Order order) {
        Optional<Customer> customerOptional = customerRepository.findById(order.getCustomer().getId());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            order.setCustomer(customer);
            Order orderUpdated = setPriceInEveryProductFromOrder(order);
            orderUpdated.setTotalOrderValue(getTotalValue(orderUpdated));
            Order savedOrder = orderRepository.save(orderUpdated);
            return Optional.of(OrderDTO.fromEntity(savedOrder));
        }
        return Optional.empty();
    }

    private Order setPriceInEveryProductFromOrder(Order order) {
        Order orderUpdated = order;
        orderUpdated.getProductsQuantities().entrySet().forEach(entry -> {
            Product product = entry.getKey();
            productRepository.findById(product.getId()).ifPresent(p -> {
                product.setPrice(p.getPrice());
            });
        });

        return orderUpdated;
    }

    @Override
    public boolean deleteOrder(String orderNumber) {
        Optional<Order> orderOptional = Optional.ofNullable(orderRepository.findByOrderNumber(orderNumber));
        orderOptional.ifPresent(order -> orderRepository.deleteById(order.getId()));
        return orderOptional.isPresent();
    }
    @Override
    public Optional<Order> getOrderById(String orderNumber) {
        return Optional.ofNullable(orderRepository.findByOrderNumber(orderNumber));
    }

    /**
     * Order’s total value is a sum of the order’s product/s prices
     * @param order
     * @return
     */
    private BigDecimal getTotalValue(Order order) {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : order.getProductsQuantities().entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            BigDecimal productPrice = product.getPrice();
            BigDecimal productTotalValue = productPrice.multiply(BigDecimal.valueOf(quantity));
            totalValue = totalValue.add(productTotalValue);
        }
        return totalValue;
    }
}
