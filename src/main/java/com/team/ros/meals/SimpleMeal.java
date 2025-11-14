package com.team.ros.meals;

public class SimpleMeal implements Meal {
    private final String id;
    private final String name;
    private final double price;
    private final int calories;
    private final MenuCategory category;

    public SimpleMeal(String id, String name, double price, int calories, MenuCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.category = category;
    }

    @Override public String id() { return id; }
    @Override public String name() { return name; }
    @Override public double price() { return price; }
    @Override public int calories() { return calories; }
    @Override public MenuCategory category() { return category; }
}
