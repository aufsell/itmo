package utility;

public enum TypeOfMoney {

    FERTING("Фертинг"),
    SANTIC("Сантик");
    private String Value;
    TypeOfMoney(String Value) {
        this.Value = Value;
    }

    public String getValue(TypeOfMoney _type) {
        TypeOfMoney type = _type;
        return type.Value;
    }
}
