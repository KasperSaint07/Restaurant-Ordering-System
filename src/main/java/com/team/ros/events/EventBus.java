package com.team.ros.events;

import java.util.ArrayList;
import java.util.List;


public class EventBus {
    private EventBus() {
    }

    private static final List<EventListener> listeners = new ArrayList<>();

    public static void subscribe(EventListener l) {
        if (l != null) listeners.add(l);
    }

    public static void publish(OrderEventType type, String orderId) {
        for (int i = 0; i < listeners.size(); i++) {
            try {
                listeners.get(i).onEvent(type, orderId);
            } catch (RuntimeException ignore) {

            }
        }
    }


    public static void clear() {
        listeners.clear();
    }
}
