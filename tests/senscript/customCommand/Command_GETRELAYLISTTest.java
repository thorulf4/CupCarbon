package senscript.customCommand;

import d504.TestableSensorNode;
import d504.routingTable.RoutingTable;
import d504.utils.Serialize;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETRELAYLISTTest {

    @Test
    void getRelayList(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("R1", "1", 3);
        routingTable.addEntry("R2", "3", 4);
        routingTable.addEntry("R2", "4", 2);
        routingTable.addEntry("R3", "5", 1);

        sensor.putVariable("routingTable", routingTable.serialize());

        Command_GETRELAYLIST command = new Command_GETRELAYLIST(sensor, "routingTable", "relayListOutput");
        command.execute();

        List<String> relays = Serialize.deserializeStringList(sensor.getVariableValue("$relayListOutput"));

        assertEquals(3, relays.size());
        assertEquals("R1&R2&R3", sensor.getVariableValue("$relayListOutput"));
    }

    @Test
    void getRelayList_EmptyRelayList(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        RoutingTable routingTable = new RoutingTable();

        sensor.putVariable("routingTable", routingTable.serialize());

        Command_GETRELAYLIST command = new Command_GETRELAYLIST(sensor, "routingTable", "relayListOutput");
        command.execute();

        List<String> relays = Serialize.deserializeStringList(sensor.getVariableValue("$relayListOutput"));

        assertEquals(0, relays.size());
        assertEquals("", sensor.getVariableValue("$relayListOutput"));
    }

}