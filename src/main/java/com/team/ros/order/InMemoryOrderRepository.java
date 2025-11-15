package com.team.ros.order;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class InMemoryOrderRepository implements OrderRepository {

    private static final InMemoryOrderRepository INSTANCE = new InMemoryOrderRepository();
    public static InMemoryOrderRepository getInstance() { return INSTANCE; }
    private InMemoryOrderRepository() {}

    // Сохраняем порядок вставки для удобного «следующий по очереди»
    private final Map<String, Order> byId = new LinkedHashMap<>();
    private final Object lock = new Object();

    @Override
    public void save(Order order) {
        if (order == null || order.getId() == null) return;
        synchronized (lock) {
            byId.put(order.getId(), order);
        }
    }

    @Override
    public Optional<Order> findById(String id) {
        if (id == null) return Optional.empty();
        synchronized (lock) {
            return Optional.ofNullable(byId.get(id));
        }
    }

    @Override
    public List<Order> findAll() {
        synchronized (lock) {
            return new ArrayList<>(byId.values());
        }
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        synchronized (lock) {
            return byId.values().stream()
                    .filter(o -> o.getStatus() == status)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean delete(String id) {
        synchronized (lock) {
            return byId.remove(id) != null;
        }
    }

    @Override
    public void clear() {
        synchronized (lock) {
            byId.clear();
        }
    }
}
