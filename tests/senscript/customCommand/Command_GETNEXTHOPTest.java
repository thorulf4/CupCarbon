package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETNEXTHOPTest {

    private TestableSensorNode sensor;

    @BeforeEach
    void beforeEach(){
        sensor = new TestableSensorNode(42);

        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("R1", "n1", 7);
        routingTable.addEntry("R1", "n2", 1);
        routingTable.addEntry("R1", "n3", 5);
        routingTable.addEntry("R2", "n3", 2);
        routingTable.addEntry("R2", "n2", 4);

        sensor.putVariable("RT", routingTable.serialize());
    }

    @Test
    void getNextHopTest01(){
        DataPackage dataPackage = new DataPackage("m1", "R1", "hello");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(3600,"n1",dataPackage);

        sensor.putVariable("MT", messageTable.serialize());

        Command_GETNEXTHOP command = new Command_GETNEXTHOP(sensor,"RT", "MT", "$dataPackage", "nextHopId");
        command.execute();

        assertEquals("n2", sensor.getVariableValue("$nextHopId"));
    }

    @Test
    void getNextHopTest02(){
        DataPackage dataPackage = new DataPackage("m1", "R1", "hello");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(3600, "n1",dataPackage);
        messageTable.addReceiver("m1", "n2");

        sensor.putVariable("MT", messageTable.serialize());

        Command_GETNEXTHOP command = new Command_GETNEXTHOP(sensor,"RT", "MT", "$dataPackage", "nextHopId");
        command.execute();

        assertEquals("n3", sensor.getVariableValue("$nextHopId"));
    }

    @Test
    void getNextHopTest03(){
        DataPackage dataPackage = new DataPackage("m1", "R1", "hello");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(3600, "n2",dataPackage);
        messageTable.addReceiver("m1", "n3");

        sensor.putVariable("MT", messageTable.serialize());

        Command_GETNEXTHOP command = new Command_GETNEXTHOP(sensor,"RT", "MT", "$dataPackage", "nextHopId");
        command.execute();

        assertEquals("n1", sensor.getVariableValue("$nextHopId"));
    }

    @Test
    void getNextHopTest04(){
        DataPackage dataPackage = new DataPackage("m1", "R2", "hello");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(3600, "n1",dataPackage);

        sensor.putVariable("MT", messageTable.serialize());

        Command_GETNEXTHOP command = new Command_GETNEXTHOP(sensor,"RT", "MT", "$dataPackage", "nextHopId");
        command.execute();

        assertEquals("n3", sensor.getVariableValue("$nextHopId"));
    }

    @Test
    void getNextHopTest05(){
        DataPackage dataPackage = new DataPackage("m1", "R2", "hello");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(3600, "n1",dataPackage);
        messageTable.addReceiver("m1", "n3");

        sensor.putVariable("MT", messageTable.serialize());

        Command_GETNEXTHOP command = new Command_GETNEXTHOP(sensor,"RT", "MT", "$dataPackage", "nextHopId");
        command.execute();

        assertEquals("n2", sensor.getVariableValue("$nextHopId"));
    }


    @Test
    void getNextHopTest06(){
        DataPackage dataPackage = new DataPackage("m1", "R2", "hello");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(3600,"n1",dataPackage);
        messageTable.addReceiver("m1", "n3");
        messageTable.addReceiver("m1", "n2");

        sensor.putVariable("MT", messageTable.serialize());

        Command_GETNEXTHOP command = new Command_GETNEXTHOP(sensor,"RT", "MT", "$dataPackage", "nextHopId");
        command.execute();

        assertEquals("n1", sensor.getVariableValue("$nextHopId"));
    }

}