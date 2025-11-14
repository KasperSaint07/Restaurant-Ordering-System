package com.team.ros.meals;

public class GeorgianMealFactory implements MealFactory {

    private static final String[] FAST  = { "GE_KHACHAPURI", "GE_KUBDARI" };
    private static final String[] DESS  = { "GE_CHURCHKHELA" };
    private static final String[] HOT   = { "GE_KHARCHO", "GE_CHASHUSHULI" };
    private static final String[] DRINK = { "GE_GEORGIAN_LEMONADE" };
    private static final String[] ALCO  = { "GE_KINDZMARAULI" };

    @Override
    public Meal create(String id) {
        switch (id) {
            case "GE_KHACHAPURI":
                return new SimpleMeal("GE_KHACHAPURI","Khachapuri",1900,680, MenuCategory.FAST_FOOD);
            case "GE_KUBDARI":
                return new SimpleMeal("GE_KUBDARI","Kubdari",2000,720, MenuCategory.FAST_FOOD);
            case "GE_CHURCHKHELA":
                return new SimpleMeal("GE_CHURCHKHELA","Churchkhela",800,300, MenuCategory.DESSERTS);
            case "GE_KHARCHO":
                return new SimpleMeal("GE_KHARCHO","Kharcho",2100,700, MenuCategory.HOT_DISHES);
            case "GE_CHASHUSHULI":
                return new SimpleMeal("GE_CHASHUSHULI","Chashushuli",2200,730, MenuCategory.HOT_DISHES);
            case "GE_GEORGIAN_LEMONADE":
                return new SimpleMeal("GE_GEORGIAN_LEMONADE","Georgian Lemonade",700,120, MenuCategory.DRINKS);
            case "GE_KINDZMARAULI":
                return new SimpleMeal("GE_KINDZMARAULI","Kindzmarauli",2600,130, MenuCategory.ALCOHOL);
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
            case DRINKS:     return DRINK;
            case ALCOHOL:    return ALCO;
            default:         return new String[0];
        }
    }
}
