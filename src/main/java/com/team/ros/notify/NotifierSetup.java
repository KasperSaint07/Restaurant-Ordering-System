package com.team.ros.notify;

import com.team.ros.events.EventBus;

public final class NotifierSetup {
    private NotifierSetup() {}
    //Подкл слушателей, вызывать 1 раз
    public static void initDefault() {
        EventBus.clear();
        EventBus.subscribe(new AuditLogger());
        EventBus.subscribe(new KitchenNotifier());
        EventBus.subscribe(new CustomerNotifier());
        EventBus.subscribe(new PaymentAlert());
    }
}
