package d504;

import d504.utils.Serialize;

public class NodeCost implements Comparable<NodeCost>{
    private String nodeId;
    private int cost;

    public NodeCost(String nodeId, int cost) {
        this.nodeId = nodeId;
        this.cost = cost;
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public int compareTo(NodeCost o) {
        return cost - o.cost;
    }

    public String serialize() {
        return nodeId + "&" + cost;
    }

    public static NodeCost deserialize(String data) {
        String[] elements = Serialize.nextElements(data, 2);
        String id = elements[0];
        int cost = Integer.parseInt(elements[1]);

        return new NodeCost(id, cost);
    }
}
