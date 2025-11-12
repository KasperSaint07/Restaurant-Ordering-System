package com.team.ros.kitchenfactory;

public interface KitchenFactory {
    String getName();

    String[] fastFood();
    String[] desserts();
    String[] hotDishes();
    String[] drinks();
    String[] alcohol();

}
