package com.team.ros.pricing;

import com.team.ros.order.Order;

public interface PricingStrategy {
    String name();
    double discount(Order order);
}
