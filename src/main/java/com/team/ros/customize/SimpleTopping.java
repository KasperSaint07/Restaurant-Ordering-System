package com.team.ros.customize;

import com.team.ros.meals.Meal;

public class SimpleTopping extends ToppingDecorator {
    private final String suffix; //+suffix to name
    private final double addPrice; //+Price
    private final int addCalories; //+Calorie

    public SimpleTopping(Meal base, String suffix, double addPrice, int addCalories) {
        super(base);
        this.suffix = suffix;
        this.addPrice = addPrice;
        this.addCalories = addCalories;
    }

    @Override public String name() { return base.name() + " "+ suffix; }
    @Override public double price() { return base.price() + addPrice; }
    @Override public int calories() { return base.calories() + addCalories; }
}