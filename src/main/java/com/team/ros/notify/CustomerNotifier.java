package com.team.ros.notify;

import com.team.ros.events.EventListener;
import com.team.ros.events.OrderEventType;

public class CustomerNotifier implements EventListener {
    @Override
    public void onEvent(OrderEventType type, String orderId) {
        switch (type) {
            case ORDER_PAID     -> System.out.println("[CLIENT] Order " + orderId + " paid. Thank you!");
            case ORDER_READY    -> System.out.println("[CLIENT] Order " + orderId + " is READY. Please pick up.");
            case ORDER_COMPLETED-> System.out.println("[CLIENT] Order " + orderId + " completed.");
            case PAYMENT_FAILED -> System.out.println("[CLIENT] Payment failed for " + orderId + ". Try again.");
            default -> {}
        }
    }
}
