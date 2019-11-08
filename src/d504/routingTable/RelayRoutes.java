package d504.routingTable;

import d504.NodeCost;
import d504.exceptions.NoRouteForRelayException;
import d504.utils.Serialize;

import java.util.Comparator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RelayRoutes {
    private String relayId;
    private SortedSet<NodeCost> routes;
    private int modifier;

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
        Optional<NodeCost> optionalNodeCost = routes.stream()
                .findFirst()
                .map(this::returnWithModifier);

        if(optionalNodeCost.isPresent()){
            return optionalNodeCost.get();
        }else{
            throw new NoRouteForRelayException("No route for relay " + relayId);
        }
    }

    public boolean hasNode(String nodeId) {
        return routes.stream().anyMatch(nc -> nc.getNodeId().equals(nodeId));
    }

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(relayId);
        stringBuilder.append('&');
        stringBuilder.append(modifier);
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
        String modifier = Serialize.nextElement(data);
        data = Serialize.removeElements(data, 1);
        int count = Integer.parseInt(Serialize.nextElement(data));
        data = Serialize.removeElements(data, 1);
        String[] elements = data.split("&", count * 2);

        RelayRoutes entry = new RelayRoutes(relayId);
        entry.setModifier(Integer.parseInt(modifier));

        for(int i = 0; i < count; i++){
            NodeCost pair = NodeCost.deserialize(data);
            data = Serialize.removeElements(data, 2);
            entry.addRoute(pair.getNodeId(), pair.getCost());
        }

        return entry;

    }

    public void removeRoute(String nodeId) {
        Optional<NodeCost> optionalNodeCost = routes.stream().filter(nc -> nc.getNodeId().equals(nodeId)).findFirst();

        optionalNodeCost.ifPresent(nodeCost -> routes.remove(nodeCost));
    }

    public SortedSet<NodeCost> getRoutes() {
        return routes.stream()
                .map(this::returnWithModifier)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public NodeCost returnWithModifier(NodeCost nodeCost){
        return new NodeCost(nodeCost.getNodeId(), nodeCost.getCost() + modifier);
    }

    public int getMaxCost() {
        Optional<NodeCost> maxNodeCost = routes.stream().max(Comparator.comparingInt(NodeCost::getCost));
        if(maxNodeCost.isPresent()){
            return maxNodeCost.get().getCost();
        }else{
            throw new NoRouteForRelayException("No Route for relay: " + relayId);
        }
    }

    public int getMinCost() {
        Optional<NodeCost> minNodeCost = routes.stream().min(Comparator.comparingInt(NodeCost::getCost));
        if(minNodeCost.isPresent()){
            return minNodeCost.get().getCost();
        }else{
            throw new NoRouteForRelayException("No Route for relay: " + relayId);
        }
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }
}
