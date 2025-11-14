package com.team.ros.checkout;

import com.team.ros.config.AppConfig;

import java.util.concurrent.ThreadLocalRandom;

public final class KaspiMockAdapter implements PaymentAdapter {
    @Override public String name() { return "KaspiMock"; }

    @Override
    public boolean pay(String orderId, double amount) {
        if (amount <= 0) return false;
        boolean simulate = AppConfig.getInstance().isSimulateRandomPaymentFailures();      // типа имитация случайных отказов, если включено
        if (simulate) {
            int rnd = ThreadLocalRandom.current().nextInt(100);
            return rnd >= 20; // 80% успех
        }
        return true;
    }
}
