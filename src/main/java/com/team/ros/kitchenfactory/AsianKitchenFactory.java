package com.team.ros.kitchenfactory;

public class AsianKitchenFactory implements KitchenFactory {
    @Override public String getName() { return "Asian"; }

    @Override public String[] fastFood()   { return new String[]{"Sushi Set", "Bao"}; }
    @Override public String[] desserts()   { return new String[]{"Mochi"}; }
    @Override public String[] hotDishes()  { return new String[]{"Ramen", "Pad Thai"}; }
    @Override public String[] drinks()     { return new String[]{"Green Tea"}; }
    @Override public String[] alcohol()     { return new String[]{"Sake"}; }
}
