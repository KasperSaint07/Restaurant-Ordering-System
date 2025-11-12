package com.team.ros.meals;

public class AsianMealFactory implements MealFactory {
    private static final String[] FAST = { "AS_SUSHI_SET", "AS_BAO"};
    private static final String[] DESS = { "AS_MOCHI"};
    private static final String[] DRNK = { "AS_RAMEN", "AS_PAD_THAI"};
    private static final String[] HOT = { "AS_GREEN_TEA"};
    private static final String[] ALCO = { "AS_SAKE"};


    @Override
    public Meal create(String id) {
        switch (id) {
            case "AS_SUSHI_SET":
                return new SimpleMeal("AS_SUSHI_SET","Sushi Set",2600,520, MenuCategory.FAST_FOOD);
            case "AS_BAO":
                return new SimpleMeal("AS_BAO","Bao",1200,350, MenuCategory.FAST_FOOD);
            case "AS_MOCHI":
                return new SimpleMeal("AS_MOCHI","Mochi",900,220, MenuCategory.DESSERTS);
            case "AS_RAMEN":
                return new SimpleMeal("AS_RAMEN","Ramen",2100,650, MenuCategory.HOT_DISHES);
            case "AS_PAD_THAI":
                return new SimpleMeal("AS_PAD_THAI","Pad Thai",2200,680, MenuCategory.HOT_DISHES);
            case "AS_GREEN_TEA":
                return new SimpleMeal("AS_GREEN_TEA","Green Tea",500,2, MenuCategory.DRINKS);
            case "AS_SAKE":
                return new SimpleMeal("AS_SAKE","Sake",2400,110, MenuCategory.ALCOHOL);
            default:
                return null;
        }
    }

    @Override
    public String[] listByCategory(MenuCategory c) {
        switch (c) {
            case FAST_FOOD:  return FAST;
            case DESSERTS:   return DESS;
            case HOT_DISHES: return HOT;
            case DRINKS:     return DRNK;
            case ALCOHOL:    return ALCO;
            default:         return new String[0];
        }
    }
}
