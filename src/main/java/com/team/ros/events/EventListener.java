package com.team.ros.events;

public interface EventListener {
    void onEvent(OrderEventType type, String orderId);
}
