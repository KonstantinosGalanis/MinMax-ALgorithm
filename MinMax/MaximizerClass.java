package MinMax;

public class MaximizerClass extends InternalNodeClass{
    public MaximizerClass() {
        super();
    }
    
    public MaximizerClass(InternalNodeClass[] children) {
        super(children);
    }

    public double getMaxValue() {
        double maxValue = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < getChildrenSize(); i++) {
            double childValue = getChild(i).getValue();
            if (childValue > maxValue) {
                maxValue = childValue;
            }
        }
        return maxValue;
    }
}
