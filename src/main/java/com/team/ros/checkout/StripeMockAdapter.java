package com.team.ros.checkout;

public final class StripeMockAdapter implements PaymentAdapter {
    @Override public String name() { return "StripeMock"; }

    @Override
    public boolean pay(String orderId, double amount) {
        if (amount <= 0) return false;
        System.out.println("[StripeMock] charge ok: order=" + orderId + ", amount=" + amount);
        return true;
    }
}
