package com.team.ros.order;

import com.team.ros.meals.Meal;
import com.team.ros.meals.MenuCategory;

public class ComboMeal implements Meal {
    private final String id;
    private final String name;
    private final double price;
    private final int calories;
    private final MenuCategory category;
    public ComboMeal(String id, String name, double price, int calories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.category = MenuCategory.FAST_FOOD;
    }

    @Override public String id() { return id; }
    @Override public String name() { return name; }
    @Override public double price() { return price; }
    @Override public int calories() { return calories; }
    @Override public MenuCategory category() { return category; }
}