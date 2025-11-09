package com.team.ros.events;

public class Event {
private OrderEventType type;
private String orderId;
private long timeMillis;

    public Event(OrderEventType type, String orderId, long timeMillis) {
        this.type = type;
        this.orderId = orderId;
        this.timeMillis = timeMillis;
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
