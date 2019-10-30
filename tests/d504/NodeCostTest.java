package d504;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeCostTest {

    @Test
    void serialize() {
        NodeCost nodeCost = new NodeCost("id", 50);
        assertEquals(nodeCost.serialize(), "id&50");
    }

    @Test
    void compareTo_shouldBeEqual_SameCostAndSameId(){
        NodeCost nodeCost = new NodeCost("A", 10);
        NodeCost nodeCost2 = new NodeCost("A", 10);

        assertEquals(0, nodeCost.compareTo(nodeCost2));
    }

    @Test
    void compareTo_shouldNotBeEqual_SameCostDifferentId(){
        NodeCost nodeCost = new NodeCost("A", 15);
        NodeCost nodeCost2 = new NodeCost("B", 10);

        assertEquals(1, nodeCost.compareTo(nodeCost2));
    }

    @Test
    void compareTo_shouldNotBeEqual_DifferentCostDifferentId(){
        NodeCost nodeCost = new NodeCost("A", 10);
        NodeCost nodeCost2 = new NodeCost("B", 15);

        assertEquals(-1, nodeCost.compareTo(nodeCost2));
    }

    @Test
    void deserialize() {
        NodeCost pair = NodeCost.deserialize("id&50");
        assertEquals("id", pair.getNodeId());
        assertEquals(50, pair.getCost());
    }
}