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
        if ("1".equals(s)) new com.team.ros.cli.UserUI().start();
        else new com.team.ros.cli.CashierUI().start();
    }
}
