package com.team.ros.meals;

public interface MealFactory {
    Meal create(String id);
    String[] listByCategory(MenuCategory c);
}
