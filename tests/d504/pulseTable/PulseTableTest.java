package d504.pulseTable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PulseTableTest {

    @Test
    void serialize() {
        PulseTable pulseTable = new PulseTable(3);
        pulseTable.pulseNeighbour("5");
        pulseTable.pulseNeighbour("2");
        pulseTable.tickAllNeighbours();
        pulseTable.pulseNeighbour("1");
        pulseTable.tickAllNeighbours();
        pulseTable.pulseNeighbour("3");

        assertEquals("3&1&2&2&1&3&3&5&1", pulseTable.serialize());
    }

    @Test
    void deserialize() {
        PulseTable pulseTable = PulseTable.deserialize("3&1&2&2&1&3&3&5&1");

        assertEquals("3&1&2&2&1&3&3&5&1", pulseTable.serialize());
    }
}