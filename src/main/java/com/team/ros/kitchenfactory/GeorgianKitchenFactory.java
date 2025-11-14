package com.team.ros.kitchenfactory;

public class GeorgianKitchenFactory implements KitchenFactory {
    @Override public String getName() { return "Georgian"; }

    @Override public String[] fastFood()   { return new String[]{"Khachapuri", "Kubdari"}; }
    @Override public String[] desserts()   { return new String[]{"Churchkhela"}; }
    @Override public String[] hotDishes()  { return new String[]{"Kharcho", "Chashushuli"}; }
    @Override public String[] drinks()     { return new String[]{"Georgian Lemonade"}; }
    @Override public String[] alcohol()     { return new String[]{"Kindzmarauli"}; }
}
