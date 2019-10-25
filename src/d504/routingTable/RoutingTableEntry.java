package d504.routingTable;

import d504.NodeCostPair;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class RoutingTableEntry {
    private String relayId;
    private SortedSet<NodeCostPair> routes;

    public RoutingTableEntry(String relayId) {
        this.relayId = relayId;
        routes = new TreeSet<NodeCostPair>();
    }

    public String getRelayId() {
        return relayId;
    }

    public void addRoute(String node, int cost) {
        Optional<NodeCostPair> optionalNodeCostPair = routes.stream().filter(nc -> nc.getNodeId().equals(node)).findFirst();

        if(optionalNodeCostPair.isPresent()){
            optionalNodeCostPair.get().setCost(cost);
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
}
