package d504;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigPackageTest {

    @Test
    void deserialize_CorrectValues() {
        String serializedConfigPackage = "1&5&2&10";

        ConfigPackage actualConfigPackage = ConfigPackage.deserialize(serializedConfigPackage);

        assertEquals(5, actualConfigPackage.getRelayTable().get(0).getCost());
        assertEquals(10, actualConfigPackage.getRelayTable().get(1).getCost());
    }

    @Test
    void deserialize_CorrectSize(){
        String serializedConfigPackage = "1&5&2&10";

        ConfigPackage actualConfigPackage = ConfigPackage.deserialize(serializedConfigPackage);

        assertEquals(2, actualConfigPackage.getRelayTable().size());
    }

    @Test
    void serialize_CorrectValues(){
        ConfigPackage configPackage = new ConfigPackage();
        configPackage.add("1",5);
        configPackage.add("2", 10);

        List<String> serializedConfigList = Arrays.asList(configPackage.serialize().split("&"));

        assertTrue(serializedConfigList.contains("1"));
        assertTrue(serializedConfigList.contains("5"));
        assertTrue(serializedConfigList.contains("2"));
        assertTrue(serializedConfigList.contains("10"));
    }

    @Test
    void serialize_CorrectSize(){
        ConfigPackage configPackage = new ConfigPackage();
        configPackage.add("1",5);
        configPackage.add("2",10);

        String actualSerializedConfig = configPackage.serialize();

        assertEquals(4, actualSerializedConfig.split("&").length);
    }
}