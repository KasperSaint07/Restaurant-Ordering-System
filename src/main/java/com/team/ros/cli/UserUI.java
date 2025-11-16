package com.team.ros.cli;

import com.team.ros.checkout.CheckoutFacade;
import com.team.ros.checkout.CheckoutResult;
import com.team.ros.config.AppConfig;
import com.team.ros.events.EventBus;
import com.team.ros.events.OrderEventType;
import com.team.ros.meals.*;
import com.team.ros.order.*;
import com.team.ros.pricing.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class UserUI {
    private final Scanner in = new Scanner(System.in);

    private OrderBuilder builder;     // конструктор заказа
    private Order current;            // текущий заказ
    private MealFactory mealFactory;  // фабрика выбранной кухни
    private final OrderRepository repo = InMemoryOrderRepository.getInstance(); // без JSON

    // Автогенерация ID заказа
    private static String nextOrderId() {
        String ts = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        int rnd = new Random().nextInt(900) + 100; // 100..999
        return "ORD-" + ts + "-" + rnd;
    }

    public void start() {
        System.out.println("=== Restaurant Ordering - User UI ===");
        while (true) {
            try {
                System.out.println();
                System.out.println("1) Menu");
                System.out.println("2) New Order");
                System.out.println("3) Show Cart Totals");
                System.out.println("4) Combo");
                System.out.println("5) Checkout");
                System.out.println("6) Select pricing strategy");
                System.out.println("0) Exit");
                System.out.print("Choose: ");
                String cmd = in.nextLine().trim();

                switch (cmd) {
                    case "1" -> showCuisineMenus();
                    case "2" -> newOrderFlow();      // авто-ID, выбор кухни
                    case "3" -> showTotals();
                    case "4" -> addCombo();          // выбор по цифрам, скидка 10%
                    case "5" -> doCheckout();        // выбор провайдера 1/2
                    case "6" -> chooseStrategy();
                    case "0" -> { return; }
                    default -> System.out.println("Unknown command.");
                }
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // ===== 1) MENU =====
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
                case "1" -> previewByFactory(new AsianMealFactory());
                case "2" -> previewByFactory(new ItalianMealFactory());
                case "3" -> previewByFactory(new GeorgianMealFactory());
                case "0" -> { return; }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    private void previewByFactory(MealFactory f) {
        System.out.println("\n== " + f.getClass().getSimpleName().replace("MealFactory","") + " menu (IDs) ==");
        for (MenuCategory c : MenuCategory.values()) {
            String[] ids = f.listByCategory(c);
            if (ids.length == 0) continue;
            System.out.println("[" + c.name() + "]");
            for (String id : ids) System.out.println(" - " + id);
        }
    }

    // ===== 2) NEW ORDER =====
    private void newOrderFlow() {
        System.out.println("\n-- New order -- ");
        String id = nextOrderId();
        System.out.println("Generated order id: " + id);

        System.out.println("Choose cuisine for this order:");
        System.out.println("1) Asian");
        System.out.println("2) Italian");
        System.out.println("3) Georgian");
        System.out.print("Choose: ");
        String pick = in.nextLine().trim();

        // создаём заказ и сразу кладём в репозиторий (видит кассир)
        builder = new OrderBuilder(id);
        current = builder.build();
        repo.save(current);
        EventBus.publish(OrderEventType.ORDER_CREATED, id);

        switch (pick) {
            case "1" -> {
                mealFactory = new AsianMealFactory();
                builder.withFactory(mealFactory);
                addLoopFromFactory();   // теперь реально добавляет из Asian
            }
            case "2" -> {
                mealFactory = new ItalianMealFactory();
                builder.withFactory(mealFactory);
                addLoopFromFactory();
            }
            case "3" -> {
                mealFactory = new GeorgianMealFactory();
                builder.withFactory(mealFactory);
                addLoopFromFactory();   // и из Georgian тоже
            }
            default -> System.out.println("Unknown cuisine. Empty order created.");
        }
    }

    private void addLoopFromFactory() {
        while (true) {
            System.out.println("\nAdd items:");
            System.out.println("1) FAST_FOOD");
            System.out.println("2) DESSERTS");
            System.out.println("3) HOT_DISHES");
            System.out.println("4) DRINKS");
            System.out.println("5) ALCOHOL");
            System.out.println("0) Done");
            System.out.print("Choose: ");
            String s = in.nextLine().trim();
            if ("0".equals(s)) {
                if (current.getItems().isEmpty()) {
                    System.out.println("Cart is empty. Add at least 1 item.");
                    continue;
                }
                repo.save(current); // обновили в репозитории
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
            addByNumber(cat); // выбор по цифрам
        }
    }

    private void addByNumber(MenuCategory cat) {
        if (mealFactory == null) {
            System.out.println("No MealFactory bound to order.");
            return;
        }
        String[] ids = mealFactory.listByCategory(cat);
        if (ids.length == 0) { System.out.println("No items in " + cat); return; }

        System.out.println("-- IDs in " + cat + " --");
        for (int i = 0; i < ids.length; i++) System.out.println((i + 1) + ") " + ids[i]);
        System.out.print("Pick number: ");
        String pick = in.nextLine().trim();
        if (!pick.matches("\\d+")) { System.out.println("Invalid number."); return; }
        int idx = Integer.parseInt(pick) - 1;
        if (idx < 0 || idx >= ids.length) { System.out.println("Out of range."); return; }

        String chosenId = ids[idx];
        System.out.print("Qty (>=1): ");
        int qty = safeInt(in.nextLine(), 1);
        builder.add(chosenId, qty);
        repo.save(current); // обновили, чтобы кассир видел
        System.out.println("Added: " + chosenId + " x" + qty);
    }

    // ===== 3) SHOW TOTALS =====
    private void showTotals() {
        requireOrder();
        double subtotal = current.totalBeforeVat();
        double disc = PricingEngine.discount(current);
        disc = Math.max(0, Math.min(disc, subtotal));
        double after = subtotal - disc;
        double vat = after * (AppConfig.getInstance().getVatPercent() / 100.0);
        double total = after + vat;

        System.out.printf(Locale.US,
                "Subtotal: %.2f | Discount(%s): %.2f | VAT: %.2f | TOTAL %.2f | Items: %d%n",
                subtotal, PricingEngine.current().name(), disc, vat, total, current.getItems().size());
    }

    // ===== 4) COMBO (по цифрам, скидка фикс 10%) =====
    private void addCombo() {
        requireOrder();
        MealFactory f = (mealFactory != null) ? mealFactory : new ItalianMealFactory();

        String id1 = pickIdByNumber(f, MenuCategory.FAST_FOOD, "Pick #1 (FAST_FOOD): ");
        if (id1 == null) return;

        String id2 = pickIdByNumber(f, MenuCategory.DRINKS, "Pick #2 (DRINKS): ");
        if (id2 == null) return;

        ComboBuilder cb = new ComboBuilder()
                .withFactory(f)
                .first(id1)
                .second(id2)
                .discount(10.0); // фикс 10%

        String comboId = "COMBO-" + System.currentTimeMillis();
        Meal combo = cb.build(comboId);
        if (combo == null) { System.out.println("Combo build failed."); return; }

        current.add(combo, 1);
        repo.save(current);
        System.out.println("Combo added: " + combo.name() + " | price=" + combo.price());
    }

    private String pickIdByNumber(MealFactory f, MenuCategory c, String prompt) {
        String[] ids = f.listByCategory(c);
        if (ids.length == 0) { System.out.println("No items in " + c); return null; }
        System.out.println("[" + c + "] ids:");
        for (int i = 0; i < ids.length; i++) System.out.println((i + 1) + ") " + ids[i]);
        System.out.print(prompt);
        String s = in.nextLine().trim();
        if (!s.matches("\\d+")) { System.out.println("Invalid number."); return null; }
        int idx = Integer.parseInt(s) - 1;
        if (idx < 0 || idx >= ids.length) { System.out.println("Out of range."); return null; }
        return ids[idx];
    }

    // ===== 5) CHECKOUT (провайдер по цифре) =====
    private void doCheckout() {
        requireOrder();
        System.out.println("Payment provider:");
        System.out.println("1) KASPI");
        System.out.println("2) STRIPE");
        System.out.print("Choose: ");
        String s = in.nextLine().trim();

        String provider = switch (s) {
            case "1" -> "KaspiMock";
            case "2" -> "StripeMock";
            default -> AppConfig.getInstance().getDefaultPaymentProvider();
        };

        CheckoutResult res = CheckoutFacade.checkout(current, provider);
        System.out.println();
        if (res.success()) {
            System.out.println("=== PAYMENT SUCCESS ===");
            repo.save(current); // статус PAID запишется в синглтон
            System.out.println(res.receiptText());
        } else {
            System.out.println("=== PAYMENT FAILED ===");
            System.out.println("Reason: " + res.reason());
        }
    }

    // ===== 6) STRATEGY =====
    private void chooseStrategy() {
        System.out.println("Pricing strategies:");
        System.out.println("1) NoDiscount");
        System.out.println("2) ComboDiscount(10%)");
        System.out.println("3) HappyHours(15:00-17:00, 20%)");
        System.out.print("Choose: ");
        String s = in.nextLine().trim();
        switch (s) {
            case "1" -> PricingEngine.use(new NoDiscount());
            case "2" -> PricingEngine.use(new ComboDiscount(10.0));
            case "3" -> PricingEngine.use(new HappyHours(15, 17, 20.0));
            default -> { System.out.println("Unknown - keep current."); return; }
        }
        System.out.println("Strategy set to: " + PricingEngine.current().name());
    }

    // ===== helpers =====
    private void requireOrder() {
        if (current == null) throw new IllegalStateException("Create order first in 2) New Order.");
    }
    private static int safeInt(String s, int def) {
        try { return Math.max(1, Integer.parseInt(s.trim())); }
        catch (Exception ignore) { return def; }
    }
}
