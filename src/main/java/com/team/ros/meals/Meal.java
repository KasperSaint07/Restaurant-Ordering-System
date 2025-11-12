package com.team.ros.meals;

public interface Meal {
    String id();
    String name();
    double price();
    int calories();
    MenuCategory category();
}
