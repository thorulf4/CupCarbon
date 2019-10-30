package d504;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ConfiguredNodesTable {
    private Set<String> configuredNodes;

    public ConfiguredNodesTable() {
        configuredNodes = new TreeSet<>();
    }

    public boolean isNodeInTable(String nodeId){
        return configuredNodes.contains(nodeId);
    }

    public void add(String nodeId){
        configuredNodes.add(nodeId);
    }

    public Collection<String> getConfiguredNodes(){
        return Collections.unmodifiableCollection(configuredNodes);
    }

    public String serialize(){
        StringBuilder serializedBuilder = new StringBuilder();
        for(String nodeId : configuredNodes){
            serializedBuilder.append("&");
            serializedBuilder.append(nodeId);
        }
        return serializedBuilder.toString();
    }

    public static ConfiguredNodesTable deserialize(String serializedTable){
        String[] nodes = serializedTable.split("&");

        ConfiguredNodesTable configuredNodesTable = new ConfiguredNodesTable();
        for(String node : nodes){
            if(!node.isEmpty())
                configuredNodesTable.add(node);
        }

        return configuredNodesTable;
    }
}
