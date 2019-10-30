package d504;

import java.util.Set;
import java.util.TreeSet;

public class ConfigPackage {

    private Set<RelayRouteCost> relayTable;
    private String senderNodeId;

    /**
     * Create empty ConfigPackage
     * @param senderNodeId Id of the sender node
     */
    public ConfigPackage(String senderNodeId) {
        this.relayTable = new TreeSet<>();
        this.senderNodeId = senderNodeId;
    }

    /**
     * Creates a ConfigPackage with costs incremented by 1
     * @param relayTable The current relayTable with the nodes current costs
     * @param senderNodeId Id of the sender node
     */
    public ConfigPackage(Set<RelayRouteCost> relayTable, String senderNodeId){
        this.relayTable = new TreeSet<>(relayTable);
        this.senderNodeId = senderNodeId;
        incrementRelayTable();
    }

    private void incrementRelayTable() {
        for(RelayRouteCost relayRouteCost : relayTable){
            relayRouteCost.setCost(relayRouteCost.getCost() + 1);
        }
    }

    public String serialize() {
        StringBuilder serializedConfigPackage = new StringBuilder();
        serializedConfigPackage.append(senderNodeId).append("&");

        for (RelayRouteCost relayRouteCost : relayTable) {
            String relayId = relayRouteCost.getRelayId();
            int cost = relayRouteCost.getCost();
            serializedConfigPackage
                    .append(relayId).append("&")
                    .append(cost).append("&");
        }
        serializedConfigPackage.delete(serializedConfigPackage.length()-1, serializedConfigPackage.length());

        return serializedConfigPackage.toString();
    }

    public void add(String relayId, int cost) {
        relayTable.add(new RelayRouteCost(relayId, cost));
    }

    public Set<RelayRouteCost> getRelayTable() {
        return relayTable;
    }

    public static ConfigPackage deserialize(String str) {
        String[] values = str.split("&");
        ConfigPackage configPackage = new ConfigPackage(values[0]);

        for(int i = 1; i < values.length; i += 2){
            configPackage.add(values[i], Integer.parseInt(values[i+1]));
        }

        return configPackage;
    }

    public String getSenderNodeId() {
        return senderNodeId;
    }
}
