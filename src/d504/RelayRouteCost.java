package d504;

import java.util.Comparator;
import java.util.Objects;

public class RelayRouteCost implements Comparable<RelayRouteCost>{
    private String relayId;
    private int cost;

    public RelayRouteCost(String relayId, int cost) {
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
        RelayRouteCost that = (RelayRouteCost) o;
        return cost == that.cost &&
                relayId.equals(that.relayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relayId, cost);
    }

    @Override
    public int compareTo(RelayRouteCost o) {
        return Comparator.comparing(RelayRouteCost::getRelayId)
                .thenComparing(RelayRouteCost::getCost)
                .compare(this, o);
    }
}
