package com.team.ros.checkout;

public final class KaspiMockAdapter implements PaymentAdapter {
    @Override public String name() { return "KaspiMock"; }


    @Override
    public boolean pay(String orderId, double amount) {
        return amount > 0;
    }

}
