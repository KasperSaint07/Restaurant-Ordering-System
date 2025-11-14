package com.team.ros.order;

import com.team.ros.config.AppConfig;
import com.team.ros.events.EventBus;
import com.team.ros.events.OrderEventType;
import com.team.ros.meals.Meal;

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

    public void add(Meal meal, int qty) {
        items.add(new OrderItem(meal, qty));
    }

    public void removeAt(int index) {
        if (index >= 0 && index < items.size()) items.remove(index);
    }

    public double totalBeforeVat() {
        double s = 0.0;
        for (int i = 0; i < items.size(); i++) s+= items.get(i).subtotal(); return s;
    }

    public double vatAmount() {
        double percent = AppConfig.getInstance().getVatPercent();
        return totalBeforeVat() * percent / 100;
    }

    public double totalWithVat() { return totalBeforeVat() + vatAmount(); }

    public void setStatus(OrderStatus newStatus) {
        this.status = newStatus;
        switch(newStatus) {
            case PAID -> EventBus.publish(OrderEventType.ORDER_PAID, id);
            case COOKING -> EventBus.publish(OrderEventType.ORDER_COOKING, id);
            case READY -> EventBus.publish(OrderEventType.ORDER_READY, id);
            case COMPLETED -> EventBus.publish(OrderEventType.ORDER_COMPLETED, id);
            default -> {}
        }
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", items=" + items.size() + ", status=" + status + ", total=" + totalWithVat() + "}";
    }
}