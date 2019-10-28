package d504.routingTable;

import d504.NodeCostPair;
import d504.utils.Serialize;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class RoutingTableEntry {
    private String relayId;
    private SortedSet<NodeCostPair> routes;

    public RoutingTableEntry(String relayId) {
        this.relayId = relayId;
        routes = new TreeSet<>();
    }

    public String getRelayId() {
        return relayId;
    }

    public void addRoute(String node, int cost) {
        Optional<NodeCostPair> optionalNodeCostPair = routes.stream().filter(nc -> nc.getNodeId().equals(node)).findFirst();

        if(optionalNodeCostPair.isPresent()){
            NodeCostPair nodeCostPair = optionalNodeCostPair.get();
            if(nodeCostPair.getCost() != cost){
                routes.remove(nodeCostPair);
                routes.add(new NodeCostPair(node, cost));
            }
        }
        else{
            routes.add(new NodeCostPair(node, cost));
        }
    }

    public NodeCostPair getLowestCost() {
        return routes.first();
    }

    public boolean hasNode(String nodeId) {
        return routes.stream().anyMatch(nc -> nc.getNodeId().equals(nodeId));
    }

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(relayId);
        stringBuilder.append('&');
        stringBuilder.append(routes.size());
        for (NodeCostPair route: routes) {
            stringBuilder.append('&');
            stringBuilder.append(route.serialize());
        }

        return stringBuilder.toString();
    }

    public int getRouteCount(){
        return routes.size();
    }

    public static RoutingTableEntry deserialize(String data) {
        String relayId = Serialize.nextElement(data);
        data = Serialize.removeElements(data, 1);
        int count = Integer.parseInt(Serialize.nextElement(data));
        data = Serialize.removeElements(data, 1);
        String[] elements = data.split("&", count * 2);

        RoutingTableEntry entry = new RoutingTableEntry(relayId);

        for(int i = 0; i < count; i++){
            NodeCostPair pair = NodeCostPair.deserialize(data);
            data = Serialize.removeElements(data, 2);
            entry.addRoute(pair.getNodeId(), pair.getCost());
        }

        return entry;

    }
}
