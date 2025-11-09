package com.team.ros.config;

public class AppConfig {
    private static AppConfig Instance = new AppConfig();
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
    }

    public boolean isAllowDiscountsForAlcohol() {
        return allowDiscountsForAlcohol;
    }

    public static void setInstance(AppConfig instance) {
        Instance = instance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setVatPercent(double vatPercent) {
        this.vatPercent = vatPercent;
    }

    public void setDemoMode(boolean demoMode) {
        this.demoMode = demoMode;
    }

    public void setSimulateRandomPaymentFailures(boolean simulateRandomPaymentFailures) {
        this.simulateRandomPaymentFailures = simulateRandomPaymentFailures;
    }

    public void setDefaultPricingStrategy(String defaultPricingStrategy) {
        this.defaultPricingStrategy = defaultPricingStrategy;
    }

    public void setDefaultPaymentProvider(String defaultPaymentProvider) {
        this.defaultPaymentProvider = defaultPaymentProvider;
    }

    public void setAllowDiscountsForAlcohol(boolean allowDiscountsForAlcohol) {
        this.allowDiscountsForAlcohol = allowDiscountsForAlcohol;
    }
}
