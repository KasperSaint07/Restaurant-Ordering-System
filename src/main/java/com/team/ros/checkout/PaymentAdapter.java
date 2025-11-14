package com.team.ros.checkout;

public interface PaymentAdapter {
    String name();
    boolean pay(String orderId, double amount);
}
