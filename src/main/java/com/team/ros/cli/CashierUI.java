package com.team.ros.cli;

import com.team.ros.config.AppConfig;
import com.team.ros.order.Order;
import com.team.ros.order.OrderItem;
import com.team.ros.order.OrderStatus;
import com.team.ros.order.Orders;
import com.team.ros.pricing.PricingEngine;
import com.team.ros.checkout.ReceiptPrinters;
import com.team.ros.order.OrderRepository;
import com.team.ros.order.InMemoryOrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CashierUI {

    private final Scanner in = new Scanner(System.in);
    private final OrderRepository repo = InMemoryOrderRepository.getInstance();

    public void start() {
        System.out.println("=== Cashier Console ===");
        while (true) {
            System.out.println();
            System.out.println("1) List orders");
            System.out.println("2) Filter by status (COOKING/READY/etc)");
            System.out.println("3) Open order by id");
            System.out.println("4) Quick status update (id -> status)");
            System.out.println("5) Take next NEW -> COOKING");
            System.out.println("6) Reprint receipt");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String cmd = in.nextLine().trim();

            switch (cmd) {
                case "1" -> listOrders();
                case "2" -> filterByStatus();
                case "3" -> openOrderFlow();
                case "4" -> quickStatus();
                case "5" -> takeNext();
                case "6" -> reprintReceipt();
                case "0" -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Unknown.");
            }
        }
    }

    // ---------- Actions ----------

    private void listOrders() {
        var all = repo.findAll();
        if (all.isEmpty()) { System.out.println("(no orders yet)"); return; }
        printOrders(all);
    }

    private void filterByStatus() {
        System.out.print("Status (NEW|PAID|COOKING|READY|COMPLETED|CANCELED): ");
        String s = in.nextLine().trim().toUpperCase(Locale.ROOT);
        OrderStatus status;
        try { status = OrderStatus.valueOf(s); }
        catch (IllegalArgumentException ex) { System.out.println("Invalid status."); return; }
        var filtered = repo.findByStatus(status);
        if (filtered.isEmpty()) { System.out.println("(no orders with status " + status + ")"); return; }
        printOrders(filtered);
    }

    private void openOrderFlow() {
        System.out.print("Enter order id: ");
        String id = in.nextLine().trim();
        Order o = repo.findById(id).orElse(null);
        if (o == null) { System.out.println("Order not found."); return; }
        orderScreen(o);
    }

    private void quickStatus() {
        System.out.print("Order id: ");
        String id = in.nextLine().trim();
        Order o = repo.findById(id).orElse(null);
        if (o == null) { System.out.println("Order not found."); return; }
        System.out.print("Set status (COOKING|READY|COMPLETED|CANCELED): ");
        String s = in.nextLine().trim().toUpperCase(Locale.ROOT);
        try {
            OrderStatus st = OrderStatus.valueOf(s);
            o.setStatus(st);
            repo.save(o); // перезаписываем (не обязательно, но логично)
            System.out.println("Updated: " + o.getId() + " -> " + st);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid status.");
        }
    }

    private void takeNext() {
        for (Order o : repo.findAll()) {
            if (o.getStatus() == OrderStatus.NEW) {
                o.setStatus(OrderStatus.COOKING);
                repo.save(o);
                System.out.println("Taken to COOKING: " + o.getId());
                return;
            }
        }
        System.out.println("No NEW orders in queue.");
    }


    /** Повторная печать чека: пересчитываем суммы и шлём в принтер-адаптер. */
    private void reprintReceipt() {
        System.out.print("Order id to reprint: ");
        String id = in.nextLine().trim();
        Order o = Orders.get(id);
        if (o == null) { System.out.println("Order not found."); return; }

        double subtotal = o.totalBeforeVat();
        double disc = PricingEngine.discount(o);
        disc = Math.max(0, Math.min(disc, subtotal));
        double after = subtotal - disc;
        double vat = after * (AppConfig.getInstance().getVatPercent() / 100.0);
        double total = after + vat;

        String provider = AppConfig.getInstance().getDefaultPaymentProvider();
        String strategy = PricingEngine.current().name();

        String text = ReceiptPrinters.defaultPrinter()
                .print(o, subtotal, disc, vat, total, provider, strategy);

        System.out.println("--- REPRINTED RECEIPT ---");
        System.out.println(text);
    }

    // ---------- Order screen ----------

    private void orderScreen(Order o) {
        while (true) {
            System.out.println("\n--- Order " + o.getId() + " ---");
            showOrderDetails(o);
            System.out.println("1) Set COOKING");
            System.out.println("2) Set READY");
            System.out.println("3) Set COMPLETED");
            System.out.println("4) Set CANCELED");
            System.out.println("5) Reprint receipt");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String s = in.nextLine().trim();

            switch (s) {
                case "1" -> o.setStatus(OrderStatus.COOKING);
                case "2" -> o.setStatus(OrderStatus.READY);
                case "3" -> o.setStatus(OrderStatus.COMPLETED);
                case "4" -> o.setStatus(OrderStatus.CANCELED);
                case "5" -> reprintDirect(o);
                case "0" -> { return; }
                default -> System.out.println("Unknown.");
            }
        }
    }

    private void reprintDirect(Order o) {
        double subtotal = o.totalBeforeVat();
        double disc = PricingEngine.discount(o);
        disc = Math.max(0, Math.min(disc, subtotal));
        double after = subtotal - disc;
        double vat = after * (AppConfig.getInstance().getVatPercent() / 100.0);
        double total = after + vat;

        String provider = AppConfig.getInstance().getDefaultPaymentProvider();
        String strategy = PricingEngine.current().name();

        String text = ReceiptPrinters.defaultPrinter()
                .print(o, subtotal, disc, vat, total, provider, strategy);

        System.out.println("--- REPRINTED RECEIPT ---");
        System.out.println(text);
    }

    // ---------- Helpers ----------

    private void showOrderDetails(Order o) {
        if (o.getItems().isEmpty()) {
            System.out.println("(empty)");
        } else {
            for (OrderItem it : o.getItems()) {
                System.out.printf(Locale.US,
                        " - %s x%d  @ %.2f  => %.2f%n",
                        it.getMealName(), it.getQuantity(), it.getUnitPrice(), it.subtotal());
            }
        }
        double subtotal = o.totalBeforeVat();
        double disc = PricingEngine.discount(o);
        disc = Math.max(0, Math.min(disc, subtotal));
        double after = subtotal - disc;
        double vat = after * (AppConfig.getInstance().getVatPercent() / 100.0);
        double total = after + vat;

        System.out.printf(Locale.US,
                "Subtotal: %.2f | Discount(%s): %.2f | VAT: %.2f | TOTAL: %.2f | Status: %s%n",
                subtotal, PricingEngine.current().name(), disc, vat, total, o.getStatus());
    }

    private void printOrders(List<Order> orders) {
        System.out.println("\nID | Status | Items | Total (with VAT)");
        System.out.println("--------------------------------------");
        for (Order o : orders) {
            double total = totalWithVat(o);
            System.out.printf(Locale.US, "%s | %s | %d | %.2f%n",
                    o.getId(), o.getStatus(), o.getItems().size(), total);
        }
    }

    private static double totalWithVat(Order o) {
        double subtotal = o.totalBeforeVat();
        double disc = PricingEngine.discount(o);
        disc = Math.max(0, Math.min(disc, subtotal));
        double after = subtotal - disc;
        double vat = after * (AppConfig.getInstance().getVatPercent() / 100.0);
        return after + vat;
    }
}
