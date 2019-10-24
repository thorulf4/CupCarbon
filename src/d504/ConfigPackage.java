package d504;

import java.util.Hashtable;
import java.util.Map;

public class ConfigPackage {

    private Hashtable<String, Integer> relayTable;

    public ConfigPackage(Hashtable<String, Integer> relayTable) {
        this.relayTable = relayTable;
    }

    public String serialize() {
        StringBuilder serializedConfigPackage = new StringBuilder();

        for (Map.Entry<String, Integer> entry : relayTable.entrySet()) {
            String relayId = entry.getKey();
            Integer value = entry.getValue();
            serializedConfigPackage.append(relayId).append("#").append(value.toString()).append("#");
        }
        serializedConfigPackage.delete(serializedConfigPackage.length()-1, serializedConfigPackage.length());

        return serializedConfigPackage.toString();
    }

    public static ConfigPackage deserialize(String str) {
        Hashtable<String, Integer> table = new Hashtable<String, Integer>();

        String[] values = str.split("#");
        for(int i = 0; i < values.length; i += 2){
            table.put(values[i], Integer.parseInt(values[i + 1]));
        }

        return new ConfigPackage(table);
    }

    public Hashtable<String, Integer> getRelayTable() {
        return relayTable;
    }
}
