package com.team.ros.customize;

import com.team.ros.meals.Meal;

public class ExtraCheese extends SimpleTopping{
    public ExtraCheese(Meal base){super(base, "+ Extra Cheese", 300, 120); }
    public class BaconPancetta extends SimpleTopping{
        public BaconPancetta(Meal base) { super(base, "+ Bacon/Pancetta", 350, 150); }
    }

    public class GlutenFreeCrust extends SimpleTopping{
        public GlutenFreeCrust(Meal base) {super(base, "+ Gluten-Free Crust", 200, -40); }
    }

    public class DoubleShot extends SimpleTopping{
        public DoubleShot(Meal base) {super(base, "+ Double Shot", 250, 3); }
    }

    public class VanillaSyrup extends SimpleTopping{
        public VanillaSyrup(Meal base) {super(base, "+ Vanilla Syrup", 150, 50); }
    }
}
