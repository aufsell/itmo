
package moves;

import java.util.Random;
import ru.ifmo.se.pokemon.*;

public class FurySwipes extends PhysicalMove {
    public FurySwipes() {
        super(Type.NORMAL, 18.0, 80.0);
    }

    protected void applyOppDamage(Pokemon var1, double var2) {
        Random var4 = new Random();
        double var5 = var4.nextDouble();
        if (var5 < 0.375) {
            super.applyOppDamage(var1, var2 * 2.0);
        } else if (var5 < 0.75) {
            super.applyOppDamage(var1, var2 * 3.0);
        } else if (var5 < 0.875) {
            super.applyOppDamage(var1, var2 * 4.0);
        } else {
            super.applyOppDamage(var1, var2 * 5.0);
        }

    }

    @Override
    protected String describe() {
        return "использует Fury Swipes";
    }
}