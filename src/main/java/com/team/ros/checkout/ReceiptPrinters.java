package com.team.ros.checkout;

public final class ReceiptPrinters {
    private static ReceiptPrinter DEFAULT = new ConsoleReceiptPrinter();
    private ReceiptPrinters() {}
    public static ReceiptPrinter defaultPrinter() { return DEFAULT; }
    public static void setDefault(ReceiptPrinter p) { if (p != null) DEFAULT = p; }
}
