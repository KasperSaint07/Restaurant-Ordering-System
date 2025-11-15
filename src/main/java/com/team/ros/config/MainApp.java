package com.team.ros.config;
import com.team.ros.cli.UserUI;
import com.team.ros.cli.CashierUI;
import com.team.ros.notify.NotifierSetup;
public class MainApp {
    public static void main(String[] args) {
        NotifierSetup.initDefault();
        new CashierUI().start();
    }
}