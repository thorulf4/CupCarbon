package d504.routingTable;

import d504.ConfigPackage;
import d504.NodeCost;
import d504.RelayRouteCost;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoutingTableTest {

    private RoutingTable routingTable;

    @BeforeEach
    public void initEach(){
        routingTable = new RoutingTable();
        routingTable.addEntry("A", "1", 10);
        routingTable.addEntry("A", "2", 5);
        routingTable.addEntry("B", "2", 1);
    }

    @Test
    void getFastetsRoute_getCorrectRoute(){

        NodeCost nodeCost = routingTable.getQuickestRouteForRelay("A");

        assertEquals("2", nodeCost.getNodeId());
        assertEquals(5, nodeCost.getCost());
    }

    @Test
    void isNodeInRoutingTable_true(){

        assertTrue(routingTable.isNodeInRoutingTable("2"));
    }

    @Test
    void isNodeInRoutingTable_false(){

        assertFalse(routingTable.isNodeInRoutingTable("10"));
    }

    @Test
    void update_changedFastestRoutes(){
        ConfigPackage configPackage = new ConfigPackage("1");
        configPackage.add("A", 2);

        boolean hasChanged = routingTable.update(configPackage);

        assertTrue(hasChanged);
    }

    @Test
    void update_unchangedFastestRoutes(){
        ConfigPackage configPackage = new ConfigPackage("1");
        configPackage.add("A", 15);

        boolean hasChanged = routingTable.update(configPackage);

        assertFalse(hasChanged);
    }

    @Test
    void removeNode(){
        assertEquals(2, routingTable.getRoutingTable().size());
        routingTable.removeNode("2");
        assertEquals(1, routingTable.getRoutingTable().size());
    }

    @Test
    void getSortedRouteListToRelay(){
        assertEquals("2",routingTable.getSortedRouteListToRelay("A").first().getNodeId());
        assertEquals("1",routingTable.getSortedRouteListToRelay("A").last().getNodeId());
    }

    @Test
    void deserialize_emptyStringReturnsEmptyRoutingTable(){
        RoutingTable routingTable = RoutingTable.deserialize("");

        Set<RelayRouteCost> configPackageEntries = routingTable.getQuickestRoutesForAllRelays();

        assertEquals(0, configPackageEntries.size());
    }

    @Test
    void serialize() {
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("relayY", "nodeX", 5);
        routingTable.addEntry("relayY", "nodeY", 3);
        routingTable.addEntry("relayX", "nodeX", 3);
        routingTable.addEntry("relayX", "nodeY", 5);
        routingTable.addEntry("relayX", "nodeZ", 7);

        assertEquals("2&relayY&0&2&nodeY&3&nodeX&5&relayX&0&3&nodeX&3&nodeY&5&nodeZ&7", routingTable.serialize());
    }

    @Test
    void removeNode_shouldRemoveEmptyRelayRouteCosts(){
        routingTable.removeNode("2");

        assertEquals(1, routingTable.getQuickestRoutesForAllRelays().size());
    }

    @Test
    void removeNodes_removingQuickestNodeShouldChangeQuickestRoute(){
        Set<RelayRouteCost> oldRoutes = routingTable.getQuickestRoutesForAllRelays();

        routingTable.removeNode("2");
        RelayRouteCost route = routingTable.getQuickestRoutesForAllRelays().stream()
                .filter(relayRouteCost -> relayRouteCost.getRelayId().equals("A"))
                .findFirst().get();

        assertEquals(10, route.getCost());
    }

    @Test
    void getRelayIds(){
        List<String> relays = routingTable.getRelayIds();
        assertEquals(2, relays.size());
    }
}