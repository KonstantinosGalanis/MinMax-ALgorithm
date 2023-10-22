package MinMax;

public class MinimizerClass extends InternalNodeClass{
    public MinimizerClass() {
        super();
    }
    
    public MinimizerClass(InternalNodeClass[] children) {
        super(children);
    }
 
    public double getMinValue() {
        double minValue = Double.POSITIVE_INFINITY;
        for (int i = 0; i < getChildrenSize(); i++) {
            double childValue = getChild(i).getValue();
            if (childValue < minValue) {
                minValue = childValue;
            }
        }
        return minValue;
    }
}
