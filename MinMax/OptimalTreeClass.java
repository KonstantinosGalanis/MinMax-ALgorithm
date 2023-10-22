package MinMax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONObject;

public class OptimalTreeClass extends ActuallTreeClass { 
    private int visitedNodes = 0;
    private InternalNodeClass rootNode;
    int minMaxCalledAB;
    int calledFromOp = 0;
    
    public OptimalTreeClass(String content) {
        minMaxCalledAB = 0;
        JSONObject jsonObj = new JSONObject(content);
        this.rootNode = buildTreeFromJSON(jsonObj);
    }
    
    @Override
    public double minMax() {
        minMaxCalledAB = 1;
        calledFromOp = 0;
        boolean temp = false;
        if(rootNode.getType().equals("max")) {
            temp = true;
        }
        else if(rootNode.getType().equals("min")) {
            temp = false;
        }
        return minMaxAB(rootNode, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, temp);
    }
    
    private double minMaxAB(InternalNodeClass node, double alpha, double beta, boolean isMaximizer) {
        if(calledFromOp == 0) {
            node.setVisited();
            visitedNodes++;
        }
        if (node.getChildrenSize() == 0) {
            return node.getValue();
        }

        if (isMaximizer) {
            double maxValue = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < node.getChildrenSize(); i++) {
                double value = minMaxAB(node.getChild(i), alpha, beta, false);
                maxValue = Math.max(maxValue, value);
                alpha = Math.max(alpha, maxValue);
                if (beta <= alpha) {
                    break;
                }
            }
            if(calledFromOp == 0) {
                node.setValue(maxValue);
            }
            return maxValue;
        } 
        else {
            double minValue = Double.POSITIVE_INFINITY;
            for (int i = 0; i < node.getChildrenSize(); i++) {
                double value = minMaxAB(node.getChild(i),alpha, beta, true);
                minValue = Math.min(minValue, value);
                beta = Math.min(beta, minValue);
                if (beta <= alpha) {
                    break;
                }
            }
            if(calledFromOp == 0) {
                node.setValue(minValue);
            }
            return minValue;
        }
    }
    
    @Override
    public ArrayList<Integer> optimalPath() {
        ArrayList<Integer> path = new ArrayList<>();
        calledFromOp = 1;
        boolean temp = false;
        if(rootNode.getType().equals("max")) {
            temp = true;
        }
        else if(rootNode.getType().equals("min")) {
            temp = false;
        }
        optimalPathAB(rootNode, path, temp);
        return path;
    }

    private void optimalPathAB(InternalNodeClass node, ArrayList<Integer> path, boolean isMaximizer) {
        if (node.getChildrenSize() == 0) {
            return;
        }
        
        if (isMaximizer) {
            double max = Double.NEGATIVE_INFINITY;
            int maxIndex = -1;
            for (int i = 0; i < node.getChildren().length; i++) {
                double childValue = minMaxAB(node.getChild(i), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
                if (childValue > max) {
                    max = childValue;
                    maxIndex = i;
                }
            }
            path.add(maxIndex);
            optimalPathAB(node.getChildren()[maxIndex], path, false);
        } 
        else {
            double min = Double.POSITIVE_INFINITY;
            int minIndex = -1;
            for (int i = 0; i < node.getChildren().length; i++) {
                double childValue = minMaxAB(node.getChild(i), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
                if (childValue < min) {
                    min = childValue;
                    minIndex = i;
                }
            }
            path.add(minIndex);
            optimalPathAB(node.getChildren()[minIndex], path, true);
        }
    }
    
    @Override
    public String toDOTString() {
        if (rootNode == null) {
            return "";
        }
        StringBuilder helper = new StringBuilder();
        helper.append("digraph BinaryTree {\n");
        toDOTStringHelperAB(rootNode, helper);
        helper.append("}\n");
        return helper.toString();
    }
    
    private void toDOTStringHelperAB(InternalNodeClass node, StringBuilder sb) {
        if (node != null) {
            if(node.getChildrenSize() == 0) {
                if (node.isVisited()) {
                    sb.append("\t").append(node.hashCode()).append(" [label=\"").append(node.getValue()).append("\"];\n");
                } 
                else {
                    sb.append("\t").append(node.hashCode()).append(" [label=\"").append(node.getValue()).append("\" color=\"red\"];\n");
                }
            }
            else {
                for (int i = 0; i < node.getChildrenSize(); i++) {
                    InternalNodeClass node2 = node.getChild(i);
                    if (node.isVisited()) {
                        sb.append("\t").append(node.hashCode()).append(" [label=\"").append(node.getValue()).append("\"];\n");
                    } 
                    else {
                        sb.append("\t").append(node.hashCode()).append(" [label=\"").append(node.getValue()).append("\" color=\"red\"];\n");
                    }
                    if(node2 != null) {
                        sb.append("\t").append(node.hashCode()).append(" -> ").append(node2.hashCode()).append(";\n");
                        toDOTStringHelperAB(node2,sb);
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelperAB(sb, rootNode);
        return sb.toString();
    }

    private void toStringHelperAB(StringBuilder sb, InternalNodeClass node) {
        sb.append("{");
        sb.append("\"type\": \"").append(node.getType()).append("\",");
        if (node.isVisited()) {
            if (node.getChildrenSize() == 0) {
                sb.append("\"value\": ").append(node.getValue()).append(",");
            } 
            else if (minMaxCalledAB == 1) {
                sb.append("\"value\": ").append(node.getValue()).append(",");
            }
        } 
        else {
            sb.append("\"pruned\": true,");
        }
        sb.append("\"children\": [");
        for (int i = 0; i < node.getChildrenSize(); i++) {
            InternalNodeClass child = node.getChild(i);
            toStringHelperAB(sb, child);
            if (i < node.getChildrenSize() - 1) {
                sb.append(",");
            }
        }
        sb.append("]}");
    }
    
    public int totalNodes(){
        int total = countTotalNodes(rootNode);
        return  total;
    }
    
    public int prunedNodes(){
        int total = countTotalNodes(rootNode);
        return  (total - visitedNodes);
    }
    
    private int countTotalNodes(InternalNodeClass node) {
        int count = 1;
        for (int i = 0; i < node.getChildrenSize(); i++) {
            InternalNodeClass child = node.getChild(i);
            count += countTotalNodes(child);
        }
        return count;
    }
    
    @Override
    public void toFile(File file) throws IOException {
        if (file.exists()) {
            throw new IOException("File already exists");
        } else {
            FileWriter writer = new FileWriter(file);
            writer.write(toString());
            writer.close();
        }
    }

    @Override
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