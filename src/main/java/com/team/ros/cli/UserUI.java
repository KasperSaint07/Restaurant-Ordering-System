package com.team.ros.cli;

import com.team.ros.meals.MealFactory;
import com.team.ros.order.Order;
import com.team.ros.order.OrderBuilder;

import java.util.Scanner;

public class UserUI {
    private final Scanner in = new Scanner(System.in);
    private OrderBuilder builder;
    private Order current;
    private MealFactory mealFactory;

    public void start() {
        System.out.println("=== Restaurant Ordering - User UI ===");
        while(true){
            try {
                System.out.println();
                System.out.println("1) Menu");
                System.out.println("2) New Order");
                System.out.println("3) Show Cart Totals ");
                System.out.println("4) Combo");
                System.out.println("5) Checkout");
                System.out.println("6) Select pricing strategy");
                System.out.println("0) Exit");
                System.out.println("Choose: ");
                String cmd = in.nextLine().trim();

                switch (cmd) {
                    case "1" -> showCuisineMenus(); // Просмотр меню по кухням
                    case "2" -> newOrderFlow(); // СОздание заказа или добавление
                    case "3" -> showTotals(); // Корзина
                    case "4" -> addCombo(); //
                    case "5" -> doCheckout(); // Оформление
                    case "6" -> chooseStrategy(); // Выбрать стратегию
                    default -> System.out.println("Unknown command.");
                }
            } catch(RuntimeException ex){
                    System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
