package core;

import utility.AbsLocation;
import utility.TypeOfLocation;



public class Location extends AbsLocation {
    private String name;
    private TypeOfLocation type;
    public Location(String _name){
        super(_name);
        this.name = _name;
        System.out.println("Место - " + name  + " успешно создано!");
    }

    public void setType(TypeOfLocation _type) {
        this.type = _type;
        String typeName = type.getValue(type);
        System.out.println(name + " объявлена как " + typeName + ".");
    }

    public String getName() {
        return name == null ? "..." : name;
    }

    public TypeOfLocation getType() {
        return type;
        }
    @Override
    public String toString() {
        return "Место " + this.getPlace();
    }
    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
    @Override
    public int hashCode() {
        return super.hashCode()+this.getPlace().hashCode();
    }
}



