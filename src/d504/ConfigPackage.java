package d504;

import java.util.ArrayList;
import java.util.List;

public class ConfigPackage {

    private List<RelayCostPair> relayTable;

    public ConfigPackage() {
        this.relayTable = new ArrayList<>();
    }

    public String serialize() {
        StringBuilder serializedConfigPackage = new StringBuilder();

        for (RelayCostPair relayCostPair : relayTable) {
            String relayId = relayCostPair.getRelayId();
            String senderNodeId = relayCostPair.getSenderNodeId();
            Integer cost = relayCostPair.getCost();
            serializedConfigPackage
                    .append(relayId).append("#")
                    .append(senderNodeId).append("#")
                    .append(cost.toString()).append("#");
        }
        serializedConfigPackage.delete(serializedConfigPackage.length()-1, serializedConfigPackage.length());

        return serializedConfigPackage.toString();
    }

    public void add(String relayId, String senderNodeId,int cost) {
        relayTable.add(new RelayCostPair(relayId, senderNodeId, cost));
    }

    public List<RelayCostPair> getRelayTable() {
        return relayTable;
    }

    public static ConfigPackage deserialize(String str) {
        ConfigPackage configPackage = new ConfigPackage();

        String[] values = str.split("#");
        for(int i = 0; i < values.length; i += 3){
            configPackage.add(values[i], values[i+1], Integer.parseInt(values[i+2]));
        }

        return configPackage;
    }


}
