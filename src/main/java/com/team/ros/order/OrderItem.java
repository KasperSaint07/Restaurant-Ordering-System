package com.team.ros.order;

import com.team.ros.meals.Meal;

public class OrderItem {
    private final String mealId;
    private final String mealName;
    private final double unitPrice;
    private int quantity;

    public OrderItem(Meal meal, int qty) {
        this.mealId = meal.id();
        this.mealName = meal.name();
        this.unitPrice = meal.price();
        this.quantity = Math.max(1, qty);
    }

    public String getMealId() { return mealId; }
    public String getMealName() { return mealName; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int qty) { Math.max(1, qty); }
    public double subtotal() { return unitPrice * quantity; }
}
