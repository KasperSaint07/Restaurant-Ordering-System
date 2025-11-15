package com.team.ros.order;

import java.util.*;

public final class Orders {
    private Orders() {}


    private static final Map<String, Order> byId = new LinkedHashMap<>();

    public static void put(Order o) {
        if (o != null) byId.put(o.getId(), o);
    }

    public static Order get(String id) {
        return id == null ? null : byId.get(id);
    }

    public static boolean exists(String id) {
        return id != null && byId.containsKey(id);
    }

    public static List<Order> list() {
        return new ArrayList<>(byId.values());
    }

    public static void clear() {
        byId.clear();
    }
}
