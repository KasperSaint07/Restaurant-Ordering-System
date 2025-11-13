package com.team.ros.notify;

import com.team.ros.events.EventListener;
import com.team.ros.events.OrderEventType;

public class AuditLogger implements EventListener {
    @Override
    public void onEvent(OrderEventType type, String orderId) {
        long t = System.currentTimeMillis();
        System.out.println("[AUDIT " + t + "] " + type + " :: " + orderId);
    }
}
