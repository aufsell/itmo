package myPokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Absol extends Pokemon {
    public Absol(String name, int level) {
        super(name, level);
        setStats(65,130,60,75,60,75);
        setType(Type.DARK);
        setMove(new DoubleTeam(), new Swagger(), new NightSlash(), new DreamEater());
    }
}
