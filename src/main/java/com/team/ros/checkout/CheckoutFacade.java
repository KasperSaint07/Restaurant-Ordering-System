package com.team.ros.checkout;

import com.team.ros.config.AppConfig;
import com.team.ros.events.EventBus;
import com.team.ros.events.OrderEventType;
import com.team.ros.order.Order;
import com.team.ros.order.OrderStatus;
import com.team.ros.pricing.PricingEngine;

public final class CheckoutFacade {
    private CheckoutFacade() {}

    public static CheckoutResult checkout(Order order, String providerName) {
        if (order == null) {
            throw new IllegalArgumentException("order == null");

            double subtotal = order.totalBeforeVat();
            double discount = PricingEngine.discount(order);
            if (discount < 0)
                discount = 0;
            if (discount > subtotal)
                discount = subtotal;

            double afterDiscount = subtotal - discount;
            double vat  = afterDiscount * (AppConfig.getInstance().getVatPercent()/100.0);
            double total = afterDiscount + vat;

            String strategyName = PricingEngine.current().name();

            String provider = (providerName == null || providerName.isBlank()) ? AppConfig.getInstance().getDefaultPaymentProvider() : providerName;

            PaymentAdapter adapter = PaymentRegistry.byName(provider);
            if (adapter == null) {
                EventBus.publish(OrderEventType.PAYMENT_FAILED, order.getId());
                return CheckoutResult.failed(subtotal, discount, vat, total, provider, strategyName, "Unknown payment provider: " + provider);
            }

            boolean paid = adapter.pay(order.getId(), total);
            if (!paid) {
                EventBus.publish(OrderEventType.PAYMENT_FAILED, order.getId());
                return CheckoutResult.failed(subtotal, discount, vat, total, provider, strategyName, "Payment rejected by provider");
            }
            order.setStatus(OrderStatus.PAID);

            String receiptText = ReceiptPrinters.defaultPrinter()
                    .print(order, subtotal, discount, vat, total, provider, strategyName);

            return CheckoutResult.success(subtotal, discount, vat, total, provider, strategyName, receiptText);
        }
    }
}
