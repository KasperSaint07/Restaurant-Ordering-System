package com.team.ros.config;

public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private AppConfig() {}
    public static AppConfig getInstance() { return INSTANCE; }

    private String currency = "KZT";
    private double vatPercent = 12.0;
    private String defaultPaymentProvider = "KaspiMock";

    public String getCurrency() { return currency; }
    public double getVatPercent() { return vatPercent; }
    public String getDefaultPaymentProvider() { return defaultPaymentProvider; }

    public void setCurrency(String v) { currency = v; }
    public void setVatPercent(double v) { vatPercent = v; }
    public void setDefaultPaymentProvider(String v) { defaultPaymentProvider = v; }
}
