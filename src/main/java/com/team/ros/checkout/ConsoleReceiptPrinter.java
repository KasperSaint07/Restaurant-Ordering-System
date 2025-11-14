package com.team.ros.checkout;

import com.team.ros.config.AppConfig;
import com.team.ros.order.Order;

public final class ConsoleReceiptPrinter implements ReceiptPrinter {
    @Override
    public String print(Order order, double subtotal, double discount, double vat, double total,
                        String provider, String strategy) {
        String cur = AppConfig.getInstance().getCurrency();
        StringBuilder sb = new StringBuilder();
        sb.append("====== RECEIPT ======\n");
        sb.append("Order: ").append(order.getId()).append("\n");
        sb.append("Items: ").append(order.getItems().size()).append("\n");
        sb.append("Strategy: ").append(strategy).append("\n");
        sb.append(String.format("Subtotal: %.2f %s%n", subtotal, cur));
        sb.append(String.format("Discount: -%.2f %s%n", discount, cur));
        sb.append(String.format("VAT (%.1f%%): %.2f %s%n",
                AppConfig.getInstance().getVatPercent(), vat, cur));
        sb.append(String.format("TOTAL: %.2f %s%n", total, cur));
        sb.append("Paid via: ").append(provider).append("\n");
        sb.append("=====================\n");

        String text = sb.toString();
        System.out.print(text);
        return text;
    }
}
