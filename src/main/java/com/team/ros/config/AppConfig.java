package com.team.ros.config;

public class AppConfig {
    private static final AppConfig Instance = new AppConfig();
    private AppConfig() {}
    public static AppConfig getInstance() {
        return Instance;
    }

    private String currency = "KZT";
    private double vatPercent = 12.0;
    private boolean demoMode = true;
    private boolean simulateRandomPaymentFailures = false;

    private String defaultPricingStrategy = "NoDiscount";
    private String defaultPaymentProvider = "KaspiMock";

    private boolean allowDiscountsForAlcohol = false;

    public String getCurrency() {
        return currency;
    }

    public double getVatPercent() {
        return vatPercent;
    }

    public boolean isDemoMode() {
        return demoMode;
    }

    public boolean isSimulateRandomPaymentFailures() {
        return simulateRandomPaymentFailures;
    }

    public String getDefaultPricingStrategy() {
        return defaultPricingStrategy;
    }

    public String getDefaultPaymentProvider() {
        return defaultPaymentProvider;
    }2

    public boolean isAllowDiscountsForAlcohol() {
        return allowDiscountsForAlcohol;
    }

    public static void setInstance(AppConfig instance) {
        Instance = instance;
    }

    public void setCurrency(String v) {
        currency = v;
    }

    public void setVatPercent(double v) {
        vatPercent = v;
    }

    public void setDemoMode(boolean v) {
        demoMode = v;
    }

    public void setSimulateRandomPaymentFailures(boolean v) {
        simulateRandomPaymentFailures = v;
    }

    public void setDefaultPricingStrategy(String v) {
        defaultPricingStrategy = v;
    }

    public void setDefaultPaymentProvider(String v) {
        defaultPaymentProvider = v;
    }

    public void setAllowDiscountsForAlcohol(boolean v) {
        allowDiscountsForAlcohol = v;
    }
}
