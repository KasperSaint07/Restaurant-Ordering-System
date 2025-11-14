package com.team.ros.order;

public class OrderItem {
    private final String mealId;
    private final String mealName;
    private final double unitPrice;
    private int quantity;

    public OrderItem(String mealId, String mealName, double unitPrice, int quantity) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.unitPrice = unitPrice;
        this.quantity = Math.max(1, quantity);
    }

    public String getMealId()   { return mealId; }
    public String getMealName() { return mealName; }
    public double getUnitPrice(){ return unitPrice; }
    public int getQuantity()    { return quantity; }
    public void setQuantity(int q) { this.quantity = Math.max(1, q); }

    public double subtotal() { return unitPrice * quantity; }
}
