package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_FINDNEXTHOPTest {

    private TestableSensorNode sensor;

    @BeforeEach
    void setUp() {
        sensor = new TestableSensorNode(1);

        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("R1", "2", 3);
        routingTable.addEntry("R1", "3", 2);
        routingTable.addEntry("R1", "5", 5);
        routingTable.addEntry("R2", "4", 3);
        routingTable.addEntry("R2", "6", 7);

        sensor.putVariable("routingTable", routingTable.serialize());
    }

    @Test
    void findNextHopTest1(){
        DataPackage dataPackage = new DataPackage("M1", "R1", "Hello world");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        Command_FINDNEXTHOP command =new Command_FINDNEXTHOP(sensor, "routingTable", "$dataPackage", "outputNode");
        command.execute();

        assertEquals("3", sensor.getVariableValue("$outputNode"));
    }

    @Test
    void findNextHopTest2(){
        DataPackage dataPackage = new DataPackage("M1", "R2", "Hello world");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        Command_FINDNEXTHOP command =new Command_FINDNEXTHOP(sensor, "routingTable", "$dataPackage", "outputNode");
        command.execute();

        assertEquals("4", sensor.getVariableValue("$outputNode"));
    }

    @Test
    void findNextHopTest3(){
        DataPackage dataPackage = new DataPackage("M1", "R3", "Hello world");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        Command_FINDNEXTHOP command =new Command_FINDNEXTHOP(sensor, "routingTable", "$dataPackage", "outputNode");

        assertThrows(RuntimeException.class, () -> command.execute());
    }

}