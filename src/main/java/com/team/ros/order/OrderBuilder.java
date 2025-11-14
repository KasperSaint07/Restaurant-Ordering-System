package com.team.ros.order;

import com.team.ros.meals.Meal;
import com.team.ros.meals.MealFactory;
import com.team.ros.meals.MenuCategory;

public class OrderBuilder {
    private final Order order;
    private MealFactory factory;

    public OrderBuilder(String orderId) { this.order = new Order(orderId); }

    public OrderBuilder withFactory(MealFactory f) {
        this.factory = f;
        return this;
    }

    public OrderBuilder add(String mealId, int qty) {
        if (factory == null) return this;
        Meal m = factory.create(mealId);
        if (m != null) order.add(m, Math.max(1,qty));
        return this;
    }

    public OrderBuilder addFirstOf(MenuCategory c, int qty) {
        if (factory == null) return this;
        String[] ids = factory.listByCategory(c);
        if (ids.length > 0) add(ids[0], qty);
        return this;
    }

    public Order build(){ return order; }
}