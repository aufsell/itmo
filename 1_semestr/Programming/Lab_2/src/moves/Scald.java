package moves;

import ru.ifmo.se.pokemon.*;

public class Scald extends SpecialMove {
    public Scald(){
        super(Type.WATER,80,100);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        if (Math.random() < 0.3) {
            Effect e1 = new Effect().turns(-1).condition(Status.BURN);
            p.setCondition(e1);
        }
    }
    @Override
    protected String describe() {
        return "использует Scald";
    }
}
