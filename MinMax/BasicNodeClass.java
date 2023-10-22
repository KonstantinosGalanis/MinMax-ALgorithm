package MinMax;

public class BasicNodeClass {
    private double value;

    public BasicNodeClass() {
        value = 0.0;
    }

    public BasicNodeClass(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
}

