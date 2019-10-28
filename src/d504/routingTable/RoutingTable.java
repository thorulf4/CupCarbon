package d504.routingTable;

import d504.ConfigPackage;
import d504.NodeCostPair;
import d504.RelayCostPair;
import d504.exceptions.NoRouteForRelayException;
import d504.utils.Serialize;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class RoutingTable {
    private List<RoutingTableEntry> routingTable;

    public RoutingTable() {
        routingTable = new ArrayList<>();
    }

    public RoutingTable(List<RoutingTableEntry> routingTable) {
        this.routingTable = routingTable;
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

    public Set<RelayCostPair> GetQuickestRoutesForRelays(){
        Set<RelayCostPair> set = new TreeSet<>();

        for (RoutingTableEntry entry: routingTable) {
            NodeCostPair lowestCost = entry.getLowestCost();
            set.add(new RelayCostPair(entry.getRelayId(), lowestCost.getCost()));
        }

        return set;
    }

    public String serialize(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(routingTable.size());
        for (RoutingTableEntry entry: routingTable) {
            stringBuilder.append('&');
            stringBuilder.append(entry.serialize());
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingTable that = (RoutingTable) o;
        return Objects.equals(routingTable, that.routingTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routingTable);
    }

    public static RoutingTable deserialize(String data){
        int firstSeperator = data.indexOf("&");
        int count = Integer.parseInt(data.substring(0, firstSeperator));
        data = data.substring(firstSeperator + 1);

        List<RoutingTableEntry> list = new ArrayList<>();

        for(int i = 0; i < count; i++){
            RoutingTableEntry entry = RoutingTableEntry.deserialize(data);
            list.add(entry);
            data = Serialize.removeElements(data, entry.getRouteCount() * 2 + 2);
        }

        return new RoutingTable(list);
    }

    public boolean update(ConfigPackage configPackage) {
        Set<RelayCostPair> oldRoutes = GetQuickestRoutesForRelays();
        String nodeId = configPackage.getSenderNodeId();

        for(RelayCostPair relayCostPair : configPackage.getRelayTable()){
            addEntry(relayCostPair.getRelayId(), nodeId, relayCostPair.getCost());
        }

        Set<RelayCostPair> newRoutes = GetQuickestRoutesForRelays();
        boolean hasChanged = !oldRoutes.equals(newRoutes);
        return hasChanged;
    }
}
