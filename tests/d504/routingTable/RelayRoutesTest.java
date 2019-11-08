package d504.routingTable;

import d504.NodeCost;
import d504.exceptions.NoRouteForRelayException;
import org.junit.jupiter.api.Test;

import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.*;

class RelayRoutesTest {

    @Test
    void serialize() {
        RelayRoutes entry = new RelayRoutes("relayX");
        entry.addRoute("nodeX", 3);
        entry.addRoute("nodeY", 5);
        entry.addRoute("nodeZ", 7);

        assertEquals("relayX&0&3&nodeX&3&nodeY&5&nodeZ&7", entry.serialize());
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
        RelayRoutes entry = RelayRoutes.deserialize("relayX&0&3&nodeX&3&nodeY&5&nodeZ&7");

        assertEquals("relayX", entry.getRelayId());
        assertTrue(entry.hasNode("nodeX"));
        assertTrue(entry.hasNode("nodeY"));
        assertTrue(entry.hasNode("nodeZ"));
        assertEquals("nodeX",entry.getLowestCost().getNodeId());

        assertEquals(3,entry.getLowestCost().getCost());
    }

    @Test
    void getMinCost_returnsLowestCostWithMultipleRoutes(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        relayRoutes.addRoute("1", 10);
        relayRoutes.addRoute("2", 20);
        relayRoutes.addRoute("3", 30);

        assertEquals(10, relayRoutes.getMinCost());
    }

    @Test
    void getMinCost_throwsExceptionWhenNoRoutes(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        assertThrows(NoRouteForRelayException.class, relayRoutes::getMinCost);
    }

    @Test
    void getMaxCost_throwsExceptionWhenNoRoutes(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        assertThrows(NoRouteForRelayException.class, relayRoutes::getMaxCost);
    }

    @Test
    void getMaxCost_returnsHighestCostWithMultipleRoutes(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        relayRoutes.addRoute("1", 10);
        relayRoutes.addRoute("2", 20);
        relayRoutes.addRoute("3", 30);

        assertEquals(30, relayRoutes.getMaxCost());
    }

    @Test
    void getLowestCost_returnsCorrectNodeID(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        relayRoutes.setModifier(5);
        relayRoutes.addRoute("1", 10);
        relayRoutes.addRoute("2", 20);

        assertEquals(15, relayRoutes.getLowestCost().getCost());
        assertEquals("1", relayRoutes.getLowestCost().getNodeId());
    }

    @Test
    void getLowestCost_noModifierSet_returnsCorrectNodeID(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        relayRoutes.addRoute("1", 10);
        relayRoutes.addRoute("2", 20);

        assertEquals(10, relayRoutes.getLowestCost().getCost());
        assertEquals("1", relayRoutes.getLowestCost().getNodeId());
    }

    @Test
    void getLowestCost_throwExceptionWhenNoRoute(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        relayRoutes.setModifier(5);

        assertThrows(NoRouteForRelayException.class, relayRoutes::getLowestCost);
    }

    @Test
    void getRoutes(){
        RelayRoutes relayRoutes = new RelayRoutes("A");

        relayRoutes.addRoute("2", 20);
        relayRoutes.addRoute("1", 10);
        relayRoutes.setModifier(5);
        SortedSet<NodeCost> routes = relayRoutes.getRoutes();

        assertEquals("1", routes.first().getNodeId());
        assertEquals(15, routes.first().getCost());
    }
}