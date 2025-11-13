package com.team.ros.notify;

import com.team.ros.events.EventListener;
import com.team.ros.events.OrderEventType;

public class KitchenNotifier implements EventListener {
    @Override
    public void onEvent(OrderEventType type, String orderId) {
        switch (type) {
            case ORDER_COOKING -> System.out.println("[KITCHEN] Start cooking " + orderId);
            case ORDER_READY   -> System.out.println("[KITCHEN] Move to pickup: " + orderId);
            default -> {} // остальное кухне pofigg
        }
    }
}
