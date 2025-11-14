package com.team.ros.meals;

public interface Meal {
    String id();          // например: "IT_PIZZA", "AS_RAMEN"
    String name();        // человекочитаемое
    double price();       // базовая цена
    int calories();       // для примера
    MenuCategory category();
}
