package com.team.ros.checkout;

public final class CheckoutResult {
    public final boolean success;
    public final double subtotal, discount, vat, total;
    public final String provider, strategy;
    public final String receipt;
    public final String message;

    private CheckoutResult(boolean success, double subtotal, double discount, double vat, double total, String provider, String strategy, String receipt, String message) {
        this.success = success;
        this.subtotal = subtotal;
        this.discount = discount;
        this.vat = vat;
        this.total = total;
        this.provider = provider;
        this.strategy = strategy;
        this.receipt = receipt;
        this.message = message;
    }




    static CheckoutResult success(double s, double d, double v, double t, String p, String st, String r) {
        return new CheckoutResult(true, s, d, v, t, p, st, r, null);
    }
    static CheckoutResult failed(double s, double d, double v, double t, String p, String st, String msg) {
        return new CheckoutResult(false, s, d, v, t, p, st,null, msg);
    }

    public boolean success() {
        return true;
    }
}
