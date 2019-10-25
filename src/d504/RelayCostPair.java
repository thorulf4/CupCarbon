package d504;

public class RelayCostPair {
    private String relayId;
    private String senderNodeId;
    private int cost;

    public RelayCostPair(String relayId, String senderNodeId, int cost) {
        this.relayId = relayId;
        this.senderNodeId = senderNodeId;
        this.cost = cost;
    }

    public String getRelayId() {
        return relayId;
    }

    public String getSenderNodeId() {
        return senderNodeId;
    }

    public int getCost() {
        return cost;
    }
}
