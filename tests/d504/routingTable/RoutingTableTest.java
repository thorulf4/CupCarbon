package d504.routingTable;

import d504.NodeCostPair;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        NodeCostPair nodeCostPair = routingTable.getFastetsRoute("A");

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
}