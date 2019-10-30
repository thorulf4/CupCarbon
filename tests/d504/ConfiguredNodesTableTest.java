package d504;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguredNodesTableTest {

    @Test
    void add() {
    }

    @Test
    void serialize() {
        ConfiguredNodesTable configuredNodesTable = new ConfiguredNodesTable();

        configuredNodesTable.add("1");
        configuredNodesTable.add("2");
        configuredNodesTable.add("3");
        String serializedTable = configuredNodesTable.serialize();

        assertEquals("&1&2&3", serializedTable);
    }

    @Test
    void deserialize_correctSize() {
        String serializedTable = "&1&2&3";

        ConfiguredNodesTable configuredNodesTable = ConfiguredNodesTable.deserialize(serializedTable);

        assertEquals(3, configuredNodesTable.getConfiguredNodes().size());
    }

    @Test
    void deserialize_correctContent() {
        String serializedTable = "&1&2&3";

        ConfiguredNodesTable configuredNodesTable = ConfiguredNodesTable.deserialize(serializedTable);
        Collection<String> nodes = configuredNodesTable.getConfiguredNodes();

        assertTrue(nodes.contains("1"));
        assertTrue(nodes.contains("2"));
        assertTrue(nodes.contains("3"));
    }

    @Test
    void deserialize_emptyString() {
        String serializedTable = "";

        ConfiguredNodesTable configuredNodesTable = ConfiguredNodesTable.deserialize(serializedTable);

        assertTrue(configuredNodesTable.getConfiguredNodes().isEmpty());
    }
}