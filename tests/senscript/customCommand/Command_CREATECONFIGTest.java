package senscript.customCommand;

import d504.*;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_CREATECONFIGTest {

    private TestableSensorNode sensor;

    @BeforeEach
    void beforeEach(){
        sensor = new TestableSensorNode(1);

        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("R1", "3", 5);
        routingTable.addEntry("R1", "4", 2);
        routingTable.addEntry("R1", "5", 3);
        routingTable.addEntry("R2", "1", 5);
        routingTable.addEntry("R2", "3", 7);

        sensor.putVariable("RT", routingTable.serialize());
    }

    @Test
    void createConfigTypedPackageTest(){


        Command_CREATECONFIG command = new Command_CREATECONFIG(sensor, "RT", "outputPacket");
        command.execute();

        TypedPackage typedPackage = TypedPackage.deserialize(sensor.getVariableValue("$outputPacket"));

        assertEquals(PackageType.Config, typedPackage.type);
        assertEquals("1", typedPackage.nodeID);
    }

    @Test
    void createConfigConfigPackageTest(){

        Command_CREATECONFIG command = new Command_CREATECONFIG(sensor, "RT", "outputPacket");
        command.execute();

        TypedPackage typedPackage = TypedPackage.deserialize(sensor.getVariableValue("$outputPacket"));
        ConfigPackage configPackage = ConfigPackage.deserialize(typedPackage.packageData);

        assertEquals("1", configPackage.getSenderNodeId(), "Expected sender id to be 1");
        assertEquals(2,configPackage.getRelayTable().size(), "Expected 2 different relays");
    }

}