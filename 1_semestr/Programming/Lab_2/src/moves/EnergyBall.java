package moves;

import ru.ifmo.se.pokemon.*;

public class EnergyBall extends SpecialMove {
    public EnergyBall() {
        super(Type.GRASS,90,100 );
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random()<=0.1) {
            Effect e1 = new Effect();
            e1.stat(Stat.SPECIAL_DEFENSE, -1);
            p.addEffect(e1);
        }
    }
    @Override
    protected String describe() {
        return "Использует Energy Ball";
    }
}