package com.team.ros.meals;

public class ItalianMealFactory implements MealFactory {
    private static final String[] FAST = { "IT_PIZZA"};
    private static final String[] DESS = { "IT_TIRAMISU"};
    private static final String[] DRNK = { "IT_ESPRESSO"};
    private static final String[] HOT = { "IT_LASAGNA"};
    private static final String[] ALCO = { "IT_PROSECCO"};

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
            case FAST_FOOD: return FAST;
            case DESSERTS: return DESS;
            case DRINKS: return DRNK;
            case HOT_DISHES: return HOT;
            case ALCOHOL: return ALCO;
            default: return new String[0];
        }
    }
}
