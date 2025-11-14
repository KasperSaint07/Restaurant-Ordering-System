package com.team.ros.customize;

import com.team.ros.meals.Meal;
import com.team.ros.meals.MenuCategory;

public class ToppingDecorator implements Meal {
    protected final Meal base;
    public ToppingDecorator(Meal base) { this.base = base; }

    @Override public String id() {return base.id(); }
    @Override public String name() { return base.name(); }
    @Override public double price() { return base.price(); }
    @Override public int calories() { return base.calories(); }
    @Override public MenuCategory category() { return base.category(); }
}