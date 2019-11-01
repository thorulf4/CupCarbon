package d504.routingTable;

import d504.NodeCost;
import d504.exceptions.AttemptedToRemoveNonExistingNodeCost;
import d504.utils.Serialize;

import javax.xml.soap.Node;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class RelayRoutes {
    private String relayId;
    private SortedSet<NodeCost> routes;

    public RelayRoutes(String relayId) {
        this.relayId = relayId;
        routes = new TreeSet<>();
    }

    public String getRelayId() {
        return relayId;
    }

    public void addRoute(String node, int cost) {
        Optional<NodeCost> optionalNodeCostPair = routes.stream().filter(nc -> nc.getNodeId().equals(node)).findFirst();

        if(optionalNodeCostPair.isPresent()){
            NodeCost nodeCost = optionalNodeCostPair.get();
            if(nodeCost.getCost() != cost){
                routes.remove(nodeCost);
                routes.add(new NodeCost(node, cost));
            }
        }
        else{
            routes.add(new NodeCost(node, cost));
        }
    }

    public NodeCost getLowestCost() {
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
        for (NodeCost route: routes) {
            stringBuilder.append('&');
            stringBuilder.append(route.serialize());
        }

        return stringBuilder.toString();
    }

    public int getRouteCount(){
        return routes.size();
    }

    public static RelayRoutes deserialize(String data) {
        String relayId = Serialize.nextElement(data);
        data = Serialize.removeElements(data, 1);
        int count = Integer.parseInt(Serialize.nextElement(data));
        data = Serialize.removeElements(data, 1);
        String[] elements = data.split("&", count * 2);

        RelayRoutes entry = new RelayRoutes(relayId);

        for(int i = 0; i < count; i++){
            NodeCost pair = NodeCost.deserialize(data);
            data = Serialize.removeElements(data, 2);
            entry.addRoute(pair.getNodeId(), pair.getCost());
        }

        return entry;

    }

    public void removeRoute(String nodeId) {
        Optional<NodeCost> optionalNodeCost = routes.stream().filter(nc -> nc.getNodeId().equals(nodeId)).findFirst();

        if(optionalNodeCost.isPresent()){
            routes.remove(optionalNodeCost.get());
        }else{
            throw new AttemptedToRemoveNonExistingNodeCost("NodeId: " + nodeId);
        }
    }
}
