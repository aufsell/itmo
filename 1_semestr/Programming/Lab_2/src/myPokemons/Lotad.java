package myPokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Lotad extends Pokemon {
    public Lotad(String name, int level) {
        super(name, level);
        setStats(40,30,30,40,50,30);
        setType(Type.WATER, Type.GRASS);
        setMove(new Scald(), new Growl());
    }
}
