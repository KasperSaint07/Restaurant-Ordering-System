package com.team.ros.checkout;

public final class CheckoutResult {
    public final boolean success;
    public final double subtotal, discount, vat, total;
    public final String provider, strategy;
    public final String receipt;
    public final String message;
    private final String receiptText;
    private final String reason;

    private CheckoutResult(boolean success, double subtotal, double discount, double vat, double total, String provider, String strategy, String receipt, String message, String receiptText, String reason) {
        this.success = success;
        this.subtotal = subtotal;
        this.discount = discount;
        this.vat = vat;
        this.total = total;
        this.provider = provider;
        this.strategy = strategy;
        this.receipt = receipt;
        this.message = message;
        this.receiptText = receiptText;
        this.reason = reason;
    }

    public String receiptText() { return receiptText; }
    public String reason() { return reason; }

    static CheckoutResult success(double s, double d, double v, double t, String p, String st, String r, String rt, String re) {
        return new CheckoutResult(true, s, d, v, t, p, st, r, rt, re,null);
    }
    static CheckoutResult failed(double s, double d, double v, double t, String p, String st, String msg, String rt, String re) {
        return new CheckoutResult(false, s, d, v, t, p, st,null, msg, rt, re);
    }

    public boolean success() {
        return true;
    }


}
