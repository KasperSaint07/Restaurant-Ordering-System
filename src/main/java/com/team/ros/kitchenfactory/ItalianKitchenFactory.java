package com.team.ros.kitchenfactory;

public class ItalianKitchenFactory implements KitchenFactory {
    @Override public String getName() { return "Italian"; }

    @Override public String[] fastFood()   { return new String[]{"Pizza Margherita", "Panini"}; }
    @Override public String[] desserts()   { return new String[]{"Tiramisu", "Panna Cotta"}; }
    @Override public String[] hotDishes()  { return new String[]{"Lasagna", "Risotto"}; }
    @Override public String[] drinks()     { return new String[]{"Espresso", "Cappuccino"}; }
    @Override public String[] alcohol()     { return new String[]{"Prosecco"}; }
}
