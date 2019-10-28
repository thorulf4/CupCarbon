package d504.routingTable;

import d504.ConfigPackage;
import d504.NodeCostPair;
import d504.RelayCostPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        NodeCostPair nodeCostPair = routingTable.getQuickestRouteForRelay("A");

        assertEquals("2", nodeCostPair.getNodeId());
        assertEquals(5, nodeCostPair.getCost());
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
    void deserialize_emptyStringReturnsEmptyRoutingTable(){
        RoutingTable routingTable = RoutingTable.deserialize("");

       Set<RelayCostPair> relayCostPairs = routingTable.getQuickestRoutesForAllRelays();

        assertEquals(0, relayCostPairs.size());
    }

    @Test
    void serialize() {
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("relayY", "nodeX", 5);
        routingTable.addEntry("relayY", "nodeY", 3);
        routingTable.addEntry("relayX", "nodeX", 3);
        routingTable.addEntry("relayX", "nodeY", 5);
        routingTable.addEntry("relayX", "nodeZ", 7);

        assertEquals("2&relayY&2&nodeY&3&nodeX&5&relayX&3&nodeX&3&nodeY&5&nodeZ&7", routingTable.serialize());
    }
}