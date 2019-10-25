package d504;

public class RelayCostPair {
    private String relayId;
    private int cost;

    public RelayCostPair(String relayId, int cost) {
        this.relayId = relayId;
        this.cost = cost;
    }

    public String getRelayId() {
        return relayId;
    }

    public int getCost() {
        return cost;
    }
}
