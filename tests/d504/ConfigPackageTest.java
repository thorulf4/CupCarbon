package d504;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class ConfigPackageTest {

    @Test
    void deserialize_CorrectValues() {
        String serializedConfigPackage = "20&A&5&B&10";

        ConfigPackage actualConfigPackage = ConfigPackage.deserialize(serializedConfigPackage);
        RelayRouteCost relayRouteCost = new RelayRouteCost("A", 5);


        assertTrue(actualConfigPackage.getRelayTable().contains(relayRouteCost));
    }

    @Test
    void deserialize_CorrectSize(){
        String serializedConfigPackage = "20&A&5&B&10";

        ConfigPackage actualConfigPackage = ConfigPackage.deserialize(serializedConfigPackage);

        assertEquals(2, actualConfigPackage.getRelayTable().size());
    }

    @Test
    void serialize_CorrectValues(){
        ConfigPackage configPackage = new ConfigPackage("1");
        configPackage.add("a",5);
        configPackage.add("b",10);

        List<String> serializedConfigList = Arrays.asList(configPackage.serialize().split("&"));

        assertTrue(serializedConfigList.contains("1"));
        assertTrue(serializedConfigList.contains("a"));
        assertTrue(serializedConfigList.contains("5"));
        assertTrue(serializedConfigList.contains("b"));
        assertTrue(serializedConfigList.contains("10"));
    }

    @Test
    void serialize_CorrectSize(){
        ConfigPackage configPackage = new ConfigPackage("1");
        configPackage.add("a",5);
        configPackage.add("b",10);

        String actualSerializedConfig = configPackage.serialize();

        assertEquals(5, actualSerializedConfig.split("&").length);
    }

    @Test
    void constructor_shouldIncrementsCosts(){
        Set<RelayRouteCost> relayRouteCosts = new TreeSet<RelayRouteCost>(){{
            add(new RelayRouteCost("2", 2));
            add(new RelayRouteCost("3", 3));
        }};

        ConfigPackage configPackage = new ConfigPackage(relayRouteCosts, "1");
        Set<RelayRouteCost> newRelayRouteCosts = configPackage.getRelayTable();

        assertTrue(newRelayRouteCosts.contains(new RelayRouteCost("2", 3)));
        assertTrue(newRelayRouteCosts.contains(new RelayRouteCost("3", 4)));
    }
}