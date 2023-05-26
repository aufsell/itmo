package core;

import utility.Itake;
import utility.TypeOfLocation;

public class Goat implements Itake {
    private String name;

    public Goat (String _name){
        this.name = _name;
        System.out.println("Объект " + name + " успешно создан!");
    }
    public String getName(){
        return name;
    }
    public void whereIsGoat(TypeOfLocation _type){
        TypeOfLocation type = _type;
        System.out.println(name + " расположился в " + type.getValue(type));
    }
    @Override
    public void takeSomething(String _thing){
        String thing = _thing;
        System.out.println(name + " достает " + thing);
    }
}
