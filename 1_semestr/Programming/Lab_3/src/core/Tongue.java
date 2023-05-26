package core;

import utility.ILeansOut;
import utility.Itake;

public class Tongue implements ILeansOut, Itake {
    private String name;
    public Tongue(String _name){
        this.name = _name;
        System.out.println("Объект " + name + " успешно создан");
    }
    @Override
    public void leansOut(){
        System.out.println(name + " высовывается из стены...(шайтан)");
    }
    @Override
    public void takeSomething(String _thing){
        System.out.println(name + " берет " + _thing + " в углубление на конце");
    }
}
