package moves;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    public Confide() {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect e1 = new Effect().turns(1).stat(Stat.SPECIAL_ATTACK, -6);
        p.addEffect(e1);
    }

    @Override
    protected String describe() {
        return "использует Confide";
    }
}


