package com.team.ros.pricing;

import com.team.ros.order.Order;

import com.team.ros.order.OrderItem;

public class ComboDiscount implements PricingStrategy {
    private final double percent;

    public ComboDiscount() {
        this(10.0);
    }
    public ComboDiscount(double percent) {
        this.percent = percent;
    }
    @Override
    public String name() {
        return "ComboDiscount("+percent+"%)";
    }
    @Override
    public double discount(Order order) {
        double comboTotal = 0.0;
        for (int i = 0; i < order.getItems().size(); i++) {
            OrderItem it = order.getItems().get(i);
            if (it.getMealName().startsWith("Combo:")) {
                comboTotal+=it.subtotal();
            }
        }
        return comboTotal * (percent/100.0);
    }
}
