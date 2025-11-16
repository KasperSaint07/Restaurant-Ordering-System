package com.team.ros.config;
import com.team.ros.cli.UserUI;
import com.team.ros.cli.CashierUI;
import com.team.ros.notify.NotifierSetup;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Start as:");
        System.out.println("1) User UI");
        System.out.println("2) Cashier UI");
        System.out.print("Choose: ");
        java.util.Scanner in = new java.util.Scanner(System.in);
        String s = in.nextLine().trim();
        while (true) {
            System.out.println("Start as:\n1) User UI\n2) Cashier UI\n0) Exit");
            String pick = in.nextLine().trim();
            switch (pick) {
                case "1" -> new UserUI().start();     // вернёшься сюда, когда в UI нажмёшь 0
                case "2" -> new CashierUI().start();  // то же
                case "0" -> { return; }
                default -> System.out.println("Unknown");
            }
        }

    }
}
