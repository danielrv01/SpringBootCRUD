package com.co.parrolabs.controller.order;


import com.co.parrolabs.repositories.entities.Order;
import com.co.parrolabs.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO) {
        Optional<OrderDTO> addedOrder = orderService.addOrder(orderDTO.toEntity());
        return addedOrder.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderNumber) {
        boolean deleted = orderService.deleteOrder(orderNumber);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("orderNumber") String orderNumber) {
        Optional<Order> order = orderService.getOrderById(orderNumber);
        return order.map(c -> ResponseEntity.ok(OrderDTO.fromEntity(c))).orElse(ResponseEntity.notFound().build());
    }
}
