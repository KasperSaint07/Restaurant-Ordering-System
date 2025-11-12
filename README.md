Restaurant Ordering System (CLI)

Small Java console app to demonstrate design patterns:
Singleton, Factory, Abstract Factory, Builder, Decorator, Facade, Adapter, Observer, Strategy.

Goal

Simulate a restaurant flow: pick kitchen & dishes, customize with toppings, apply pricing strategy, checkout (pay), and push status updates to Kitchen/Client views.


Main Features (MVP)

Kitchens: Italian / Asian / Georgian; categories: Fast Food, Desserts, Hot Dishes, Drinks, Alcohol

Cart with toppings (Decorator)

Pricing strategies: NoDiscount, ComboDiscount, HappyHours

Checkout via Facade + payment Adapter (KaspiMock, StripeMock)

Events (Observer): ORDER_PAID, COOKING, READY, COMPLETED → Kitchen / Client view

Project structure:

src/main/java/com/team/ros
  /cli            # console screens (Cashier, Kitchen, Client)
  /config         # AppConfig (Singleton)
  /events         # EventBus + events (Observer)
  /notify         # listeners (KitchenDisplay, CustomerNotifier, AuditLogger)
  /kitchenfactory # Abstract factories per kitchen
  /meals          # factories & dish models
  /customize      # toppings (Decorator)
  /order          # Order, OrderItem, builders
  /pricing        # Pricing strategies
  /checkout       # CheckoutFacade
  /integration    # Payment adapters

Where patterns live

Singleton: config/AppConfig, events/EventBus

Abstract Factory / Factory: kitchenfactory/*, meals/*

Builder: order/ComboBuilder, order/OrderBuilder

Decorator: customize/* (toppings)

Strategy: pricing/*

Adapter: integration/* (payments)

Facade: checkout/CheckoutFacade

Observer: events/EventBus + notify/*
