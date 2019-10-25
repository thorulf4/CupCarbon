package d504;

public class NodeCostPair implements Comparable<NodeCostPair>{
    private String nodeId;
    private int cost;

    public NodeCostPair(String nodeId, int cost) {
        this.nodeId = nodeId;
        this.cost = cost;
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(NodeCostPair o) {
        return Integer.compare(cost, o.cost);
    }
}
