package com.team.ros.order;

import com.team.ros.meals.Meal;
import com.team.ros.meals.MealFactory;

public class ComboBuilder {
    private MealFactory factory;
    private Meal first;
    private Meal second;
    private double discountPercent = 10.0; // default 10% off

    public ComboBuilder withFactory(MealFactory f){
        this.factory = f;
        return this;
    }

    public ComboBuilder first(String mealId) {
        if (factory != null) this.first = factory.create(mealId);
        return this;
    }

    public ComboBuilder second(String mealId) {
        if (factory != null) this.second = factory.create(mealId);
        return this;
    }

    public ComboBuilder discount(double percent) {
        if (percent >= 0 && percent <= 100) this.discountPercent = percent;
        return this;
    }
    public Meal build(String comboId) {
        if (first == null || second == null) return null;
        String name = "Combo: " + first.name() + " " + second.name();
        double base = first.price() + second.price();
        double price = base * (1.0 - discountPercent / 100.0);
        int calories = first.calories() + second.calories();
        return new ComboMeal(comboId, name, price, calories);
    }
}