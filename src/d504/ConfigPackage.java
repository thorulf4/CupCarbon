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
            Integer value = relayCostPair.getCost();
            serializedConfigPackage.append(relayId).append("#").append(value.toString()).append("#");
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
        ConfigPackage configPackage = new ConfigPackage();

        String[] values = str.split("#");
        for(int i = 0; i < values.length; i += 2){
            configPackage.add(values[i], Integer.parseInt(values[i+1]));
        }

        return configPackage;
    }


}
