package com.team.ros.order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(String id);
    List<Order> findAll();
    List<Order> findByStatus(OrderStatus status);
    boolean delete(String id);
    void clear();
}
