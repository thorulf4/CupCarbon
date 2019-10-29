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