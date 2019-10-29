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
    void deserialize() {
        NodeCost pair = NodeCost.deserialize("id&50");
        assertEquals("id", pair.getNodeId());
        assertEquals(50, pair.getCost());
    }
}