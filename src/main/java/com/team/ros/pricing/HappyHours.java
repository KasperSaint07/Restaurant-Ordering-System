package com.team.ros.pricing;

import java.time.LocalTime;

public class HappyHours implements PricingStrategy {
    private final int fromHour;
    private final int toHour;
    private final double percent;

    public HappyHours(int fromHour, int toHour, double percent) {
        this.fromHour = fromHour;
        this.toHour = toHour;
        this.percent = percent;
    }
    @Override
    public String name() {
        return "Happy Hours(" + fromHour + "-" + toHour + "," + percent + " %)";
    }
    @Override
    public double discount(Order order) {
        int h = LocalTime.now().getHour();
        if (h < fromHour || h > toHour)
            return 0;
        return order.totalBeforeVat() * (percent / 100.0);
    }
}
