package com.team.ros.pricing;

import com.team.ros.order.Order;

public class NoDiscount implements PricingStrategy {

    @Override
    public String name() {
        return "NoDiscount";
    }

    @Override
    public double discount(Order order) {
        return 0.0;
    }
}

