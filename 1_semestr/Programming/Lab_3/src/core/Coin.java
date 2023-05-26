package core;
import utility.TypeOfMoney;

public class Coin {

    private String name;
    private String Value;

    public Coin(String _name){
        this.name = _name;
        System.out.println("Объект " + name +  " успешно создан!(в кармане козла)");
    }
    public void setValue(TypeOfMoney _value){
        Value = _value.getValue(_value);
        System.out.println("Достоинство " + name +  " - один " + Value);
    }
    public String getName() {
        return name;
    }

    public String getValue() {
        return Value;
    }
}
