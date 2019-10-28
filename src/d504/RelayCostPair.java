package d504;

import java.util.Comparator;
import java.util.Objects;

public class RelayCostPair implements Comparable<RelayCostPair>{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelayCostPair that = (RelayCostPair) o;
        return cost == that.cost &&
                relayId.equals(that.relayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relayId, cost);
    }

    @Override
    public int compareTo(RelayCostPair o) {
        return Comparator.comparing(RelayCostPair::getRelayId)
                .thenComparing(RelayCostPair::getCost)
                .compare(this, o);
    }
}
