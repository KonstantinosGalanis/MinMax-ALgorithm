package MinMax;

public class InternalNodeClass extends BasicNodeClass {
    private InternalNodeClass[] children;
    private double alpha = Double.NEGATIVE_INFINITY;
    private double beta = Double.POSITIVE_INFINITY;
    private boolean visited = false;

    public InternalNodeClass() {
        super();
    }

    public InternalNodeClass(InternalNodeClass[] children) {
        super();
        this.children = children;
    }
    
    public InternalNodeClass(double value) {
        super(value);
    }
     
    public final void setChildrenSize(int size) {
        children = new InternalNodeClass[size];
    }

    public int getChildrenSize() {
        if(children != null) {
            return children.length;
        }
        else return 0;
    }

    public final void insertChild(int pos, InternalNodeClass X) {
        children[pos] = X;
    }

    public InternalNodeClass getChild(int pos) {
        return  children[pos];
    }

    public InternalNodeClass[] getChildren() {
        return children;
    }
    
    public String getType() {
        if (this instanceof MinimizerClass) {
            return "min";
        } else if (this instanceof MaximizerClass) {
            return "max";
        } else {
            return "leaf";
        }
    }
    
    public void setVisited() {
        visited = true;
    }
    
    public boolean isVisited() {
        return visited;
    }
    
    public double getMaxValue() {
        MaximizerClass maximizer = new MaximizerClass(getChildren());
        return maximizer.getMaxValue();
    }
    
    public double getMinValue() {
        MinimizerClass minimizer = new MinimizerClass(getChildren());
        return minimizer.getMinValue();
    }
    
    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }
}
