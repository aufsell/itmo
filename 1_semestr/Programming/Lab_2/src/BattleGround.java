import myPokemons.*;
import ru.ifmo.se.pokemon.Battle;

class BattleGround {
    public static void main(String args[]) {
        Battle b = new Battle();

        Absol p1 = new Absol("Пупа", 1);
        Riolu p2 = new Riolu("Терпила", 2);
        Lucario p3 = new Lucario("Дед", 2);
        Lotad p4 = new Lotad("Лупа", 1);
        Lombre p5 = new Lombre("Стратег", 3);
        Ludicolo p6 = new Ludicolo("Эгоист", 1);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);

        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);

        b.go();
    }
}