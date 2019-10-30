package d504.routingTable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelayRoutesTest {

    @Test
    void serialize() {
        RelayRoutes entry = new RelayRoutes("relayX");
        entry.addRoute("nodeX", 3);
        entry.addRoute("nodeY", 5);
        entry.addRoute("nodeZ", 7);

        assertEquals("relayX&3&nodeX&3&nodeY&5&nodeZ&7", entry.serialize());
    }

    @Test
    void test(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        relayRoutes.addRoute("1", 5);
        relayRoutes.addRoute("2", 10);
        relayRoutes.addRoute("3", 15);

        assertEquals("1", relayRoutes.getLowestCost().getNodeId());
        assertEquals(5, relayRoutes.getLowestCost().getCost());
    }

    @Test
    void deserialize() {
        RelayRoutes entry = RelayRoutes.deserialize("relayX&3&nodeX&3&nodeY&5&nodeZ&7");

        assertEquals("relayX", entry.getRelayId());
        assertTrue(entry.hasNode("nodeX"));
        assertTrue(entry.hasNode("nodeY"));
        assertTrue(entry.hasNode("nodeZ"));
        assertEquals("nodeX",entry.getLowestCost().getNodeId());

        assertEquals(3,entry.getLowestCost().getCost());
    }
}