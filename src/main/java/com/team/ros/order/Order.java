package com.team.ros.order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String id;
    private final long createdMillis = System.currentTimeMillis();
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.NEW;

    public Order(String id) { this.id = id; }

    public String getId() { return id; }
    public long getCreatedMillis() { return createdMillis; }
    public OrderStatus getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }

    public void add(String mealId, String mealName, double price, int qty) {
        items.add(new OrderItem(mealId, mealName, price, qty));
    }

    public void removeAt(int index) {
        if (index >= 0 && index < items.size()) items.remove(index);
    }

    public double totalBeforeVat() {
        double s = 0.0;
        for (int i = 0; i < items.size(); i++) s += items.get(i).subtotal();
        return s;
    }

    public void setStatus(OrderStatus s) { this.status = s; }
}
