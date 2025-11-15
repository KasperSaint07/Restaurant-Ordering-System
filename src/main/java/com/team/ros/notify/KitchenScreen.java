package com.team.ros.notify;

import com.team.ros.events.EventListener;
import com.team.ros.events.OrderEventType;

/** Псевдо-экран кухни: реагирует на ключевые события и печатает обновления. */
public class KitchenScreen implements EventListener {
    @Override
    public void onEvent(OrderEventType type, String orderId) {
        switch (type) {
            case ORDER_COOKING -> System.out.println("[KITCHEN-SCREEN]  In progress: " + orderId);
            case ORDER_READY   -> System.out.println("[KITCHEN-SCREEN]  READY for pickup: " + orderId);
            case ORDER_COMPLETED -> System.out.println("[KITCHEN-SCREEN] Completed: " + orderId);
            default -> {}
        }
    }
}
