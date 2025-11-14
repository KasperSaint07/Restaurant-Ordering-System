// src/main/java/com/team/ros/checkout/PaymentRegistry.java
package com.team.ros.checkout;

import java.util.HashMap;
import java.util.Map;

public final class PaymentRegistry {
    private static final Map<String, PaymentAdapter> MAP = new HashMap<>();
    private PaymentRegistry() {}

    static {
        register(new KaspiMockAdapter());
        register(new StripeMockAdapter());
    }

    public static void register(PaymentAdapter a) {
        if (a != null) MAP.put(a.name(), a);
    }

    public static PaymentAdapter byName(String name) {
        return MAP.get(name);
    }
}
