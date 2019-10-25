package d504;

import java.util.ArrayList;
import java.util.List;

public class ConfigPackage {

    private List<RelayCostPair> relayTable;
    private String senderNodeId;

    public ConfigPackage(String sendeNodeId) {
        this.relayTable = new ArrayList<>();
        this.senderNodeId = sendeNodeId;
    }

    public ConfigPackage(List<RelayCostPair> relayTable, String senderNodeId){
        this.relayTable = relayTable;
        this.senderNodeId = senderNodeId;
    }

    public String serialize() {
        StringBuilder serializedConfigPackage = new StringBuilder();
        serializedConfigPackage.append(senderNodeId).append("&");

        for (RelayCostPair relayCostPair : relayTable) {
            String relayId = relayCostPair.getRelayId();
            int cost = relayCostPair.getCost();
            serializedConfigPackage
                    .append(relayId).append("&")
                    .append(cost).append("&");
        }
        serializedConfigPackage.delete(serializedConfigPackage.length()-1, serializedConfigPackage.length());

        return serializedConfigPackage.toString();
    }

    public void add(String relayId, int cost) {
        relayTable.add(new RelayCostPair(relayId, cost));
    }

    public List<RelayCostPair> getRelayTable() {
        return relayTable;
    }

    public static ConfigPackage deserialize(String str) {
        String[] values = str.split("&");
        ConfigPackage configPackage = new ConfigPackage(values[0]);

        for(int i = 1; i < values.length; i += 3){
            configPackage.add(values[i], Integer.parseInt(values[i+1]));
        }

        return configPackage;
    }


}
