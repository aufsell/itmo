package utility;
//TODO
public abstract class AbsLocation {
    protected String name;

    protected AbsLocation(String _name) {
        name = _name;
    }

    @Override
    public int hashCode() {
        return super.hashCode()+this.getPlace().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        return "Место " + this.getPlace();
    }
    public String getPlace() {
        return name;
    }
}

