package moves;

import ru.ifmo.se.pokemon.*;

public class Growl extends StatusMove {
    public Growl() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect e1 = new Effect().turns(-1).stat(Stat.ATTACK, -1);
        p.addEffect(e1);
    }

    @Override
    protected String describe() {
        return "использует Growl";
    }
}