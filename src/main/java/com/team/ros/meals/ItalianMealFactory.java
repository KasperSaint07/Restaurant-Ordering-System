package com.team.ros.meals;

public class ItalianMealFactory implements MealFactory {

    // IDs по категориям
    private static final String[] FAST  = { "IT_PIZZA", "IT_PANINI" };
    private static final String[] DESS  = { "IT_TIRAMISU", "IT_PANNA_COTTA" };
    private static final String[] HOT   = { "IT_LASAGNA", "IT_RISOTTO" };
    private static final String[] DRINK = { "IT_ESPRESSO", "IT_CAPPUCCINO" };
    private static final String[] ALCO  = { "IT_PROSECCO" };

    @Override
    public Meal create(String id) {
        switch (id) {
            case "IT_PIZZA":
                return new SimpleMeal("IT_PIZZA","Pizza Margherita",2000,750, MenuCategory.FAST_FOOD);
            case "IT_PANINI":
                return new SimpleMeal("IT_PANINI","Panini",1400,500, MenuCategory.FAST_FOOD);
            case "IT_TIRAMISU":
                return new SimpleMeal("IT_TIRAMISU","Tiramisu",1500,420, MenuCategory.DESSERTS);
            case "IT_PANNA_COTTA":
                return new SimpleMeal("IT_PANNA_COTTA","Panna Cotta",1400,380, MenuCategory.DESSERTS);
            case "IT_LASAGNA":
                return new SimpleMeal("IT_LASAGNA","Lasagna",2300,700, MenuCategory.HOT_DISHES);
            case "IT_RISOTTO":
                return new SimpleMeal("IT_RISOTTO","Risotto",2200,650, MenuCategory.HOT_DISHES);
            case "IT_ESPRESSO":
                return new SimpleMeal("IT_ESPRESSO","Espresso",600,5, MenuCategory.DRINKS);
            case "IT_CAPPUCCINO":
                return new SimpleMeal("IT_CAPPUCCINO","Cappuccino",800,90, MenuCategory.DRINKS);
            case "IT_PROSECCO":
                return new SimpleMeal("IT_PROSECCO","Prosecco",2500,120, MenuCategory.ALCOHOL);
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
