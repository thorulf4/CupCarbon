package senscript.customCommand;

import d504.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Command_CREATERELAYCONFIGTest {

    @Test
    void makeConfig(){
        TestableSensorNode sensor = new TestableSensorNode(2);
        String output = "x";

        Command_CREATERELAYCONFIG config = new Command_CREATERELAYCONFIG(sensor, output);
        config.execute();

        TypedPackage result = TypedPackage.deserialize(sensor.getVariableValue("$"+output));
        ConfigPackage configResult = ConfigPackage.deserialize(result.packageData);
        RelayRouteCost expected = new RelayRouteCost("2", 1);

        assertEquals("2" ,result.nodeID);
        assertEquals(PackageType.Config, result.type);
        assertEquals("2",configResult.getSenderNodeId());
        assertEquals(1, configResult.getRelayTable().size());
        assertTrue(configResult.getRelayTable().contains(expected));
        assertEquals("0&2&2&2&1", sensor.getVariableValue("$"+output));

    }
}
