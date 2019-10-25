package d504;

import d504.utils.Serialize;

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

    public String serialize() {
        return nodeId + "&" + cost;
    }

    public static NodeCostPair deserialize(String data) {
        String[] elements = Serialize.nextElements(data, 2);
        String id = elements[0];
        int cost = Integer.parseInt(elements[1]);

        return new NodeCostPair(id, cost);
    }
}
