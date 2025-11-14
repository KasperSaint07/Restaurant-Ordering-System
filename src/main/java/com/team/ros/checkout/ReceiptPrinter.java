package com.team.ros.checkout;

import com.team.ros.order.Order;

public interface ReceiptPrinter {
    String print(Order order, double subtotal, double discount, double vat, double total,
                 String provider, String strategy);
}
