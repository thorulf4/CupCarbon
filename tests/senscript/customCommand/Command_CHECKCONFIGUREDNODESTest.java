package senscript.customCommand;

import d504.ConfiguredNodesTable;
import d504.PulseMessage;
import d504.TestableSensorNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class Command_CHECKCONFIGUREDNODESTest {

    @Test
    void nodeIspresent(){
        TestableSensorNode sensor = new TestableSensorNode(2);
        ConfiguredNodesTable configuredNodesTable = new ConfiguredNodesTable();
        String cntVar = "cntVar";
        String output = "x";
        PulseMessage puls = new PulseMessage("8");
        String pulsVar = "pulsVar";

        configuredNodesTable.add("1");
        configuredNodesTable.add("4");
        configuredNodesTable.add("8");
        configuredNodesTable.add("9");
        sensor.putVariable(cntVar,configuredNodesTable.serialize());
        sensor.putVariable(pulsVar,puls.serialize());

        Command_CHECKCONFIGUREDNODES ccfn = new Command_CHECKCONFIGUREDNODES(sensor,cntVar,"$"+pulsVar,output);
        ccfn.execute();

        assertEquals("true",sensor.getVariableValue("$"+output));
        ConfiguredNodesTable postExTable = ConfiguredNodesTable.deserialize(sensor.getVariableValue("$"+cntVar));
        for (String og:configuredNodesTable.getConfiguredNodes()) {
            assertTrue(postExTable.getConfiguredNodes().contains(og));
        }
    }

    @Test
    void nodeNotPresent(){
        TestableSensorNode sensor = new TestableSensorNode(1);
        ConfiguredNodesTable configuredNodesTable = new ConfiguredNodesTable();
        String cntVar = "cntVar";
        String output = "x";
        PulseMessage puls = new PulseMessage("8");
        String pulsVar = "pulsVar";

        configuredNodesTable.add("2");
        configuredNodesTable.add("4");
        configuredNodesTable.add("9");
        sensor.putVariable(cntVar,configuredNodesTable.serialize());
        sensor.putVariable(pulsVar,puls.serialize());

        Command_CHECKCONFIGUREDNODES ccfn = new Command_CHECKCONFIGUREDNODES(sensor,cntVar,"$"+pulsVar,output);
        ccfn.execute();

        configuredNodesTable.add("8");

        assertEquals("false",sensor.getVariableValue("$"+output));
        ConfiguredNodesTable postExTable = ConfiguredNodesTable.deserialize(sensor.getVariableValue("$"+cntVar));
        for (String og:configuredNodesTable.getConfiguredNodes()) {
            assertTrue(postExTable.getConfiguredNodes().contains(og));
        }

    }

}
