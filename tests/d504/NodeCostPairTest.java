package d504;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeCostPairTest {

    @Test
    void serialize() {
        NodeCostPair nodeCostPair = new NodeCostPair("id", 50);
        assertEquals(nodeCostPair.serialize(), "id&50");
    }

    @Test
    void deserialize() {
        NodeCostPair pair = NodeCostPair.deserialize("id&50");
        assertEquals("id", pair.getNodeId());
        assertEquals(50, pair.getCost());
    }
}