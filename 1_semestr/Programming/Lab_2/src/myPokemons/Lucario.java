package myPokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Lucario extends Riolu {
    public Lucario(String name, int level) {
        super(name, level);
        setStats(25, 35, 40, 20, 30, 80);
        addType(Type.STEEL);
        addMove(new StoneEdge());

    }
}
