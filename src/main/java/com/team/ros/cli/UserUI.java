package com.team.ros.cli;

import com.team.ros.checkout.CheckoutFacade;
import com.team.ros.checkout.CheckoutResult;
import com.team.ros.config.AppConfig;
import com.team.ros.kitchenfactory.AsianKitchenFactory;
import com.team.ros.kitchenfactory.GeorgianKitchenFactory;
import com.team.ros.kitchenfactory.KitchenFactory;
import com.team.ros.meals.ItalianMealFactory;
import com.team.ros.meals.Meal;
import com.team.ros.meals.MealFactory;
import com.team.ros.meals.MenuCategory;
import com.team.ros.order.ComboBuilder;
import com.team.ros.order.Order;
import com.team.ros.order.OrderBuilder;
import com.team.ros.pricing.ComboDiscount;
import com.team.ros.pricing.HappyHours;
import com.team.ros.pricing.NoDiscount;
import com.team.ros.pricing.PricingEngine;
import jdk.jfr.Frequency;

import java.util.Locale;
import java.util.Scanner;

public class UserUI {
    private final Scanner in = new Scanner(System.in);
    private OrderBuilder builder;    // Конструктурирует заказ
    private Order current;           // Текущий заказ
    private MealFactory mealFactory; // Для добавления (сейчас - italian)

    public void start() {
        System.out.println("=== Restaurant Ordering - User UI ===");
        while (true) {
            try {
                System.out.println();
                System.out.println("1) Menu");
                System.out.println("2) New Order");
                System.out.println("3) Show Cart Totals ");
                System.out.println("4) Combo");
                System.out.println("5) Checkout");
                System.out.println("6) Select pricing strategy");
                System.out.println("0) Exit");
                System.out.print("Choose: ");
                String cmd = in.nextLine().trim();

                switch (cmd) {
                    case "1" -> showCuisineMenus();  // Просмотр меню по кухням
                    case "2" -> newOrderFlow();      // СОздание заказа или добавление
                    case "3" -> showTotals();        // Корзина
                    case "4" -> addCombo();          // Конструктор комбо (Italian)
                    case "5" -> doCheckout();        // Оформление
                    case "6" -> chooseStrategy();    // Выбрать стратегию
                    default -> System.out.println("Unknown command.");
                }
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // ================== 1) MENU =================
    private void showCuisineMenus() {
        while (true) {
            System.out.println("\n-- MENU --");
            System.out.println("1) Asian");
            System.out.println("2) Italian");
            System.out.println("3) Georgian");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String cmd = in.nextLine().trim();
            switch (cmd) {
                case "1" -> previewKitchen(new AsianKitchenFactory());
                case "2" -> previewItalian();
                case "3" -> previewKitchen(new GeorgianKitchenFactory());
                case "0" -> {
                    return;
                }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    // Показ меню кухни, у которой пока нет цен/ккал/Id
    private void previewKitchen(KitchenFactory k) {
        System.out.println("\n== " + k.getName() + " menu ==");
        printCategory("FAST_FOOD", k.fastFood());
        printCategory("DESSERTS", k.desserts());
        printCategory("HOT_DISHES", k.hotDishes());
        printCategory("DRINKS", k.drinks());
        printCategory("ALCOHOL", k.alcohol());
        System.out.println("(Preview only - add to cart will be available when MealFactory is implemented.)");
    }

    // Компактный показ Italian из MealFactory
    private void previewItalian() {
        MealFactory it = new ItalianMealFactory();
        System.out.println("\n== Italian menu (IDs) ==");
        for (MenuCategory c : MenuCategory.values()) {
            String[] ids = it.listByCategory(c);
            if (ids.length == 0) continue;
            System.out.println("[" + "]");
            for (String id : ids) System.out.println(" - " + id);
        }
    }

    private static void printCategory(String title, String[] items) {
        if (items == null || items.length == 0) return;
        System.out.println("[" + title + "]");
        for (String s : items) System.out.println(" - " + s);
    }

    // ================== 2) NEW ORDER =================
    private void newOrderFlow() {
        System.out.println("\n-- New order -- ");
        System.out.print("Enter order id: ");
        String id = in.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Order id cannot be empty.");
            return;
        }

        //Выбор кухни
        System.out.println("Choose cuisine for this order:");
        System.out.println("1) Asian (preview only now)");
        System.out.println("2) Italian (can add items)");
        System.out.println("3) Georgian (preview only now)");
        System.out.print("Choose: ");
        String pick = in.nextLine().trim();

        //Создаем каркас заказа
        builder = new OrderBuilder(id);
        current = builder.build();

        switch (pick) {
            case "1" -> {
                System.out.println("Asian selected. You can preview menu in 1) Menu.\n" + " To actually add items, implement MealFactory/adapter for Asian.");
                addLoopItalian(false); //предложим при желании добавить из italian
            }
            case "2" -> {
                //Полноценное добавление из Italian
                mealFactory = new ItalianMealFactory();
                builder.withFactory(mealFactory);
                addLoopItalian(true);
            }
            case "3" -> {
                System.out.println("Georgian selected. YOu can preview menu in 1) Menu.\n" + "To actually add items, implement MealFactory/adapter for Georgian.");
                addLoopItalian(false);
            }
            default -> System.out.println("Unknown - created empty order.");
        }
    }

    // Цикл добавления Italian-позиций
    private void addLoopItalian(boolean mustAdd) {
        while (true) {
            System.out.println("\nAdd items (Italian):");
            System.out.println("1) FAST FOOD");
            System.out.println("2) DESSERTS");
            System.out.println("3) HOT_DISHES");
            System.out.println("4) DRINKS");
            System.out.println("5) ALCOHOL");
            System.out.println("0) Done");
            System.out.print("Choose: ");
            String s = in.nextLine().trim();
            if ("0".equals(s)) {
                if (mustAdd && current.getItems().isEmpty()) {
                    System.out.println("Cart is empty. Add at least 1 item.");
                    continue;
                }
                return;
            }
            MenuCategory cat = switch (s) {
                case "1" -> MenuCategory.FAST_FOOD;
                case "2" -> MenuCategory.DESSERTS;
                case "3" -> MenuCategory.HOT_DISHES;
                case "4" -> MenuCategory.DRINKS;
                case "5" -> MenuCategory.ALCOHOL;
                default -> null;
            };
            if (cat == null) {
                System.out.println("Unknown");
                continue;
            }
            addFromFactory(cat);
        }
    }

    private void addFromFactory(MenuCategory cat) {
        if (mealFactory == null) {
            System.out.println("No MealFactory bound to order (Italian only for now).");
            return;
        }
        String[] ids = mealFactory.listByCategory(cat);
        if (ids.length == 0) {
            System.out.println("No items in " + cat);
            return;
        }

        System.out.println("-- IDs in " + cat + " --");
        for (int i = 0; i < ids.length; i++) System.out.println((i + 1) + ") " + ids[i]);
        System.out.print("Enter id (copy) or number: ");
        String pick = in.nextLine().trim();

        String chosenId = null;
        if (pick.matches("\\d+")) {
            int idx = Integer.parseInt(pick) - 1;
            if (idx >= 0 && idx < ids.length) chosenId = ids[idx];
        } else {
            for (String s : ids)
                if (s.equalsIgnoreCase(pick)) {
                    chosenId = s;
                    break;
                }
        }
        if (chosenId == null) {
            System.out.println("Invalid choice.");
            return;
        }
        System.out.println("Qty (>=1): ");
        int qty = safeInt(in.nextLine(), 1);
        builder.add(chosenId, qty);
        System.out.println("Added: " + chosenId + " x" + qty);
    }

    // ================== 3) SHOW TOTALS =================
    private void showTotals() {
        requireOrder();
        double subtotal = current.totalBeforeVat();
        double disc = com.team.ros.pricing.PricingEngine.discount(current);
        disc = Math.max(0, Math.min(disc, subtotal));
        double after = subtotal - disc;
        double vat = after * (AppConfig.getInstance().getVatPercent() / 100.0);
        double total = after + vat;

        System.out.printf(Locale.US, "Subtotal: %.2f | Discount(%s): %.2f | VAT: %.2f | TOTAL %.2f | Items: %d%n", subtotal,
                com.team.ros.pricing.PricingEngine.current().name(), disc, vat, total, current.getItems().size());
    }

    // ================== 4) COMBO (Italian)  =================
    private void addCombo() {
        requireOrder();
        //для комбо нужен MealFactory -> используем Italian
        MealFactory it = new ItalianMealFactory();
        ComboBuilder cb = new ComboBuilder().withFactory(it);

        System.out.println("-- Build Combo (Italian IDs) --");
        String id1 = pickId(it, MenuCategory.FAST_FOOD, "First item id");
        if (id1 == null) return;
        cb.first(id1);

        String id2 = pickId(it, MenuCategory.DRINKS, "Second item id");
        if (id2 == null) return;
        cb.second(id2);

        System.out.println("Discount percent (0..100, default 10): ");
        double p = safeDouble(in.nextLine(), 10.0);
        cb.discount(Math.max(0, Math.min(100, p)));

        System.out.println("Combo id: ");
        String comboId = in.nextLine().trim();
        if (comboId.isEmpty()) comboId = "COMBO_ " + System.currentTimeMillis();

        Meal combo = cb.build(comboId);
        if(combo == null) {
            System.out.println("Combo build failed.");
            return;
        }

        current.add(combo, 1);
        System.out.println("Combo added: " + combo.name() + " | price=" + combo.price());
    }

    // ================== 5) CHECKOUT =================
    private void doCheckout() {
        requireOrder();
        System.out.println("Payment provider (enter for default=" + AppConfig.getInstance().getDefaultPaymentProvider() + "): ");
        String provider = in.nextLine().trim();
        if (provider.isEmpty()) provider = AppConfig.getInstance().getDefaultPaymentProvider();

        CheckoutResult res = CheckoutFacade.checkout(current, provider);
        System.out.println();
        if (res.success()) {
            System.out.println("=== PAYMENT SUCCESS ===");
            // Will be updated
        } else {
            System.out.println("=== PAYMENT FAILED ===");
            System.out.println("Reason: " + res); //Will be updated too
        }
    }

    // ================== 6) PRICING STRATEGY =================
    private void chooseStrategy() {
        System.out.println("Pricing strategies:");
        System.out.println("1) NoDiscount");
        System.out.println("2) ComboDiscount(10%)");
        System.out.println("3) HappyHours(15:00-17:00, 20%)");
        System.out.print("Choose: ");
        String s = in.nextLine().trim();
        switch(s) {
            case "1" -> PricingEngine.use(new NoDiscount());
            case "2" -> PricingEngine.use(new ComboDiscount(10.0));
            case "3" -> PricingEngine.use(new HappyHours(15, 17, 20.0));
            default -> {
                System.out.println("Unknown - keep current.");
                return;
            }
        }
        System.out.println("Strategy set to: " + PricingEngine.current().name());
    }

    private String pickId(MealFactory f, MenuCategory c, String prompt) {
        String[] ids = f.listByCategory(c);
        if (ids.length == 0) {
            System.out.println("No items in " + c);
            return null;
        }
        System.out.println("[" + c + "] ids:");
        for (String id : ids) System.out.println(" - " + id);
        System.out.print(prompt + ": ");
        String s = in.nextLine().trim();
        for (String id : ids) if (id.equalsIgnoreCase(s)) return id;
        System.out.println("Invalid id.");
        return null;
    }

    //===================== helpers =====================
    private void requireOrder() {
        if (current == null) throw new IllegalStateException("Create order first in 2) New Order.");
    }

    private static int safeInt(String s, int def) {
        try {
            return Math.max(1, Integer.parseInt(s.trim()));
        } catch (Exception ignore) {
            return def;
        }
    }

    private static double safeDouble(String s, double def) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception ignore) {
            return def;
        }
    }
}
