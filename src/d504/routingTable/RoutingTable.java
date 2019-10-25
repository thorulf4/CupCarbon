package d504.routingTable;

import d504.NodeCostPair;
import d504.RelayCostPair;
import d504.exceptions.NoRouteForRelayException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoutingTable {
    private List<RoutingTableEntry> routingTable;

    public RoutingTable() {
        routingTable = new ArrayList<>();
    }

    public void addEntry(String relayId, String node, int cost) {
        Optional<RoutingTableEntry> optionalRoutingTableEntry = getEntryForRelay(relayId);

        if(optionalRoutingTableEntry.isPresent()){
            optionalRoutingTableEntry.get().addRoute(node, cost);
        }else{
            RoutingTableEntry routingTableEntry = new RoutingTableEntry(relayId);
            routingTableEntry.addRoute(node, cost);
            routingTable.add(routingTableEntry);
        }
    }

    private Optional<RoutingTableEntry> getEntryForRelay(String relayId) {
        return routingTable.stream().filter(entry -> entry.getRelayId().equals(relayId)).findFirst();
    }

    public NodeCostPair getFastetsRoute(String relayId) {
        Optional<RoutingTableEntry> optionalRoutingTableEntry = getEntryForRelay(relayId);
        if(optionalRoutingTableEntry.isPresent()){
            return optionalRoutingTableEntry.get().getLowestCost();
        }
        else{
            throw new NoRouteForRelayException("No route for relay: " + relayId);
        }
    }

    public boolean isNodeInRoutingTable(String nodeId){
        for (RoutingTableEntry routingTableEntry : routingTable){
            if(routingTableEntry.hasNode(nodeId)){
                return true;
            }
        }
        return false;
    }

    public List<RelayCostPair> GetQuickestRoutesForRelays(){
        List<RelayCostPair> list = new ArrayList<>();

        for (RoutingTableEntry entry: routingTable) {
            NodeCostPair lowestCost = entry.getLowestCost();
            list.add(new RelayCostPair(entry.getRelayId(), lowestCost.getCost()));
        }

        return list;
    }

    public String serialize(){
        throw new NotImplementedException();
    };

    public static RoutingTable deserialize(String data){
        throw new NotImplementedException();
    }
}
