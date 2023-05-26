package utility;

public enum TypeOfLocation {

    ROOM("Комната"),
    OTHERS("Какое-то рандомное место");
    private String Value;

    private TypeOfLocation(String _Value){
        this.Value = _Value;
    }
    public String getValue(TypeOfLocation _type) {
        TypeOfLocation type = _type;
        return type.Value;
    }
}
