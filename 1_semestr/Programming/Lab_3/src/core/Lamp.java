package core;

import utility.ILampLifeSpan;

public class Lamp implements ILampLifeSpan {
    private String name;

    public Lamp(String name) {
        this.name = name;
        System.out.println("Объект " + name + " успешно создан!");
    }
    @Override
    public void lightUp(){
        System.out.println(name + " Пипец как ярко горит ");
    }

    @Override
    public void goOut(){
        System.out.println(name + " гаснет :(");
    }

    public String getName() {
        return name;
    }
}
