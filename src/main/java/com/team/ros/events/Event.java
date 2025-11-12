package com.team.ros.events;

public class Event {
private final OrderEventType type;
private final String orderId;
private final long timeMillis;

    public Event(OrderEventType type, String orderId) {
        this.type = type;
        this.orderId = orderId;
        this.timeMillis = System.currentTimeMillis();
    }

    public OrderEventType getType() {
        return type;
    }

    public String getOrderId() {
        return orderId;
    }

    public long getTimeMillis() {
        return timeMillis;
    }
}
