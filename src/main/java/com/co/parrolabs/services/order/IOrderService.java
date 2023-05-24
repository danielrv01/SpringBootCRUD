package com.co.parrolabs.services.order;

import com.co.parrolabs.controller.order.OrderDTO;
import com.co.parrolabs.repositories.entities.Order;

import java.util.Optional;

public interface IOrderService {

    Optional<OrderDTO> addOrder(Order order);

    boolean deleteOrder(String orderNumber);

    Optional<Order> getOrderById(String orderNumber);
}
