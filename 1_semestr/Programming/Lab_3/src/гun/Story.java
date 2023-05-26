package гun;
import core.*;
import utility.*;

public class Story {
    public static void main(String[] args){
        Location room = new Location("Локация 1");
        room.setType(TypeOfLocation.ROOM);
        Goat goat = new Goat("Козлик");
        goat.whereIsGoat(room.getType());
        Coin coin = new Coin("Монета");
        coin.setValue(TypeOfMoney.SANTIC);
        Lamp lamp = new Lamp("Лампа");
        lamp.goOut();
        Tongue tongue = new Tongue("Язычок");
        tongue.leansOut();
        goat.takeSomething(coin.getName());
        tongue.takeSomething(coin.getName());
        lamp.lightUp();
        System.out.println("Happy END");
    }
}
