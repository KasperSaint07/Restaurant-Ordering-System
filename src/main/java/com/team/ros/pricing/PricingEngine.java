package com.team.ros.pricing;

import com.team.ros.config.AppConfig;

import java.time.LocalTime;

public class PricingEngine {
    private PricingEngine() {}

    private static PricingStrategy strategy = new NoDiscount();

    public static void use(PricingStrategy s) {
        if (s!=null) strategy = s;
    }
    public static PricingStrategy current() {
        return strategy;
    }
    public static double totalWithVat(Order order) {
        double subtotal = order.totalBeforeVat();
        double disc = strategy.discount(order);
        if (disc<0)
            disc = 0;
        if (disc>subtotal)
            disc = subtotal;
        double afterDisc = subtotal - disc;

        double vat = afterDiscount * AppConfig.getInstance().getVatPercent()/ 100.0;
        return afterDiscount + vat;
        }
        public static double discount(Order order) {
        double d = strategy.discount(order);
        double subtotal = order.totalBeforeVat();
        if (d<0)
            d = 0;
        if (d>subtotal)
            d = subtotal;
        return d;
    }
}
