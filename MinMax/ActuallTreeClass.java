package MinMax;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ActuallTreeClass {
    private InternalNodeClass root;
    public InternalNodeClass rootForALL;
    int minMaxCalled;
    int calledFromOp = 0;
    
    public ActuallTreeClass() {
        this.root = null;
    }
    
    public ActuallTreeClass(String json) throws JSONException {
        minMaxCalled = 0;
        JSONObject jsonObj = new JSONObject(json);
        this.root = buildTreeFromJSON(jsonObj);
    }

    public ActuallTreeClass(File file) throws JSONException, FileNotFoundException {
        minMaxCalled = 0;
        Scanner scanner = new Scanner(file);
        String json = scanner.useDelimiter("\\Z").next();
        scanner.close();
        JSONObject jsonObj = new JSONObject(json);
        this.root = buildTreeFromJSON(jsonObj);
    }

    public InternalNodeClass buildTreeFromJSON(JSONObject obj) throws JSONException {
        String type = obj.getString("type");

        if (type.equals("leaf")) {
            double value = obj.getDouble("value");
            return new InternalNodeClass(value);
        }
        else if (type.equals("max")) {
            JSONArray childrenArr = obj.getJSONArray("children");
            InternalNodeClass[] children = new InternalNodeClass[childrenArr.length()];
            InternalNodeClass MaxChild = new MaximizerClass(children);
            for (int i = 0; i < childrenArr.length(); i++) {
                JSONObject childObj = childrenArr.getJSONObject(i);
                children[i] = buildTreeFromJSON(childObj);
                MaxChild.insertChild(i, children[i]);
            }
            return MaxChild;
        }
        else if (type.equals("min")) {
            JSONArray childrenArr = obj.getJSONArray("children");
            InternalNodeClass[] children = new InternalNodeClass[childrenArr.length()];
            InternalNodeClass MinChild = new MinimizerClass(children);
            for (int i = 0; i < childrenArr.length(); i++) {
                JSONObject childObj = childrenArr.getJSONObject(i);
                children[i] = buildTreeFromJSON(childObj);
                MinChild.insertChild(i, children[i]);
            }
            return MinChild;
        }
        else {
            throw new JSONException("Invalid node type: " + type);
        }
    }
    
    public double minMax() {
        minMaxCalled = 1;
        boolean temp = false;
        calledFromOp = 0;
        if(root.getType().equals("max")) {
            temp = true;
        }
        else if(root.getType().equals("min")) {
            temp = false;
        }
        return minMax(root, temp);
    }

    private double minMax(InternalNodeClass node, boolean isMaximizer) {
        if (node.getChildrenSize() == 0) {
            return node.getValue();
        }

        if (isMaximizer) {
            double maxValue = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < node.getChildrenSize(); i++) {
                minMax(node.getChild(i), false);
                maxValue = node.getMaxValue();
            }
            if(calledFromOp == 0) {
                node.setValue(maxValue);
            }
            return maxValue;
        } 
        else {
            double minValue = Double.POSITIVE_INFINITY;
            for (int i = 0; i < node.getChildrenSize(); i++) {
                minMax(node.getChild(i), true);
                minValue = node.getMinValue();
            }
            if(calledFromOp == 0) {
                node.setValue(minValue);
            }
            return minValue;
        }
    }

    public ArrayList<Integer> optimalPath() {
        ArrayList<Integer> path = new ArrayList<>();
        boolean temp = false;
        calledFromOp = 1;
        if(root.getType().equals("max")) {
            temp = true;
        }
        else if(root.getType().equals("min")) {
            temp = false;
        }
        optimalPath(root, path, temp);
        return path;
    }

    private void optimalPath(InternalNodeClass node, ArrayList<Integer> path, boolean isMaximizer) {
        if (node.getChildrenSize() == 0) {
            return;
        }

        if (isMaximizer) {
            double max = Double.NEGATIVE_INFINITY;
            int maxIndex = -1;
            for (int i = 0; i < node.getChildren().length; i++) {
                double childValue = minMax(node.getChild(i), false);
                if (childValue > max) {
                    max = childValue;
                    maxIndex = i;
                }
            }
            path.add(maxIndex);
            optimalPath(node.getChildren()[maxIndex], path, false);
        } 
        else {
            double min = java.lang.Double.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < node.getChildren().length; i++) {
                double childValue = minMax(node.getChild(i), true);
                if (childValue < min) {
                    min = childValue;
                    minIndex = i;
                }
            }
            path.add(minIndex);
            optimalPath(node.getChildren()[minIndex], path, true);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelper(sb, root);
        return sb.toString();
    }

    private void toStringHelper(StringBuilder sb, InternalNodeClass node) {
        sb.append("{");
        sb.append("\"type\": \"").append(node.getType()).append("\",");
        if (node.getChildrenSize() == 0) {
            sb.append("\"value\": ").append(node.getValue()).append("}");
            return;
        }
        if (minMaxCalled == 1) {
            sb.append("\"value\": ").append(node.getValue()).append(",");
        }
        sb.append("\"children\": [");
        for (int i = 0; i < node.getChildrenSize(); i++) {
            InternalNodeClass child = node.getChild(i);
            toStringHelper(sb, child);
            if (i < node.getChildrenSize() - 1) {
                sb.append(",");
            }
        }
        sb.append("]}");
    }
 
    public String toDOTString() {
        if (root == null) {
            return "";
        }
        StringBuilder helper = new StringBuilder();
        helper.append("digraph BinaryTree {\n");
        toDOTStringHelper(root, helper);
        helper.append("}\n");
        return helper.toString();
    }
    
    private void toDOTStringHelper(InternalNodeClass node, StringBuilder sb) {
        if (node != null) {
            if(node.getChildrenSize() == 0) {
                sb.append("\t").append(node.hashCode()).append(" [label=\"").append(node.getValue()).append("\"];\n");
            }
            else {
                for (int i = 0; i < node.getChildrenSize(); i++) {
                    InternalNodeClass node2 = node.getChild(i);
                    sb.append("\t").append(node.hashCode()).append(" [label=\"").append(node.getValue()).append("\"];\n");
                    if(node2 != null) {
                        sb.append("\t").append(node.hashCode()).append(" -> ").append(node2.hashCode()).append(";\n");
                        toDOTStringHelper(node2,sb);
                    }
                }
            }
        }
    }

    public void toFile(File file) throws IOException {
        if (file.exists()) {
            throw new IOException("File already exists");
        } else {
            FileWriter writer = new FileWriter(file);
            writer.write(toString());
            writer.close();
        }
    }

    public void toDotFile(File file) throws IOException {
        if (file.exists()) {
            throw new IOException("File already exists");
        } else {
            FileWriter writer = new FileWriter(file);
            writer.write(toDOTString());
            writer.close();
        }
    }
}
