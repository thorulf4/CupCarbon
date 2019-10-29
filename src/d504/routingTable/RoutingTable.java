package d504.routingTable;

import d504.ConfigPackage;
import d504.NodeCost;
import d504.RelayRouteCost;
import d504.exceptions.NoRouteForRelayException;
import d504.utils.Serialize;

import java.util.*;

public class RoutingTable {
    private List<RelayRoutes> routingTable;

    public RoutingTable() {
        routingTable = new ArrayList<>();
    }

    public RoutingTable(List<RelayRoutes> routingTable) {
        this.routingTable = routingTable;
    }

    public void addEntry(String relayId, String node, int cost) {
        Optional<RelayRoutes> optionalRoutingTableEntry = getEntryForRelay(relayId);

        if(optionalRoutingTableEntry.isPresent()){
            optionalRoutingTableEntry.get().addRoute(node, cost);
        }else{
            RelayRoutes relayRoutes = new RelayRoutes(relayId);
            relayRoutes.addRoute(node, cost);
            routingTable.add(relayRoutes);
        }
    }

    private Optional<RelayRoutes> getEntryForRelay(String relayId) {
        return routingTable.stream().filter(entry -> entry.getRelayId().equals(relayId)).findFirst();
    }

    public NodeCost getQuickestRouteForRelay(String relayId) {
        Optional<RelayRoutes> optionalRoutingTableEntry = getEntryForRelay(relayId);
        if(optionalRoutingTableEntry.isPresent()){
            return optionalRoutingTableEntry.get().getLowestCost();
        }
        else{
            throw new NoRouteForRelayException("No route for relay: " + relayId);
        }
    }

    public boolean isNodeInRoutingTable(String nodeId){
        return routingTable.stream().anyMatch(entry -> entry.hasNode(nodeId));
    }

    public Set<RelayRouteCost> getQuickestRoutesForAllRelays(){
        Set<RelayRouteCost> set = new TreeSet<>();

        for (RelayRoutes entry: routingTable) {
            NodeCost lowestCost = entry.getLowestCost();
            set.add(new RelayRouteCost(entry.getRelayId(), lowestCost.getCost()));
        }

        return set;
    }

    public boolean update(ConfigPackage configPackage) {
        Set<RelayRouteCost> oldRoutes = getQuickestRoutesForAllRelays();
        String nodeId = configPackage.getSenderNodeId();

        for(RelayRouteCost relayRouteCost : configPackage.getRelayTable()){
            addEntry(relayRouteCost.getRelayId(), nodeId, relayRouteCost.getCost());
        }

        Set<RelayRouteCost> newRoutes = getQuickestRoutesForAllRelays();
        return !oldRoutes.equals(newRoutes);
    }

    public String serialize(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(routingTable.size());
        for (RelayRoutes entry: routingTable) {
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
        int firstSeparator = data.indexOf("&");
        if(firstSeparator == -1){
            return new RoutingTable(new ArrayList<>());
        }

        int tableSize = getTableSize(data, firstSeparator);
        data = data.substring(firstSeparator + 1);

        List<RelayRoutes> list = createRoutingTableFromSerializedData(data, tableSize);

        return new RoutingTable(list);
    }

    private static List<RelayRoutes> createRoutingTableFromSerializedData(String data, int tableSize) {
        List<RelayRoutes> list = new ArrayList<>();
        for(int i = 0; i < tableSize; i++){
            RelayRoutes entry = RelayRoutes.deserialize(data);
            list.add(entry);
            data = Serialize.removeElements(data, entry.getRouteCount() * 2 + 2);
        }
        return list;
    }

    private static int getTableSize(String data, int firstSeparator) {
        return Integer.parseInt(data.substring(0, firstSeparator));
    }
}
