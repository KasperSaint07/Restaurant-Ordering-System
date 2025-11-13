package com.team.ros.notify;

import com.team.ros.events.EventListener;
import com.team.ros.events.OrderEventType;

public class PaymentAlert implements EventListener {
    @Override
    public void onEvent(OrderEventType type, String orderId) {
        if (type == OrderEventType.PAYMENT_FAILED) {
            System.out.println("[ALERT] PAYMENT FAILED -> escalate: " + orderId);
        }
    }
}
