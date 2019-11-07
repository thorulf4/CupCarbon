package senscript.customCommand;

import d504.TestableSensorNode;
import d504.pulseTable.PulseTable;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_REGISTERPULSEFORCONFIGTest {

    private TestableSensorNode sensor;

    @BeforeEach
    void setUp() {
        sensor = new TestableSensorNode(1);
        RoutingTable routingTable = new RoutingTable();
        PulseTable pulseTable = new PulseTable(1);

        routingTable.addEntry("R1", "R1", 1);
        routingTable.addEntry("R1", "2",3);
        routingTable.addEntry("R1", "3", 2);
        routingTable.addEntry("R2", "4", 3);

        sensor.putVariable("routingTable", routingTable.serialize());
        sensor.putVariable("pulseTable", pulseTable.serialize());
    }

    @Test
    void registerPulseForConfig(){
        sensor.putVariable("sender", "2");


        Command_REGISTERPULSEFORCONFIG command = new Command_REGISTERPULSEFORCONFIG(sensor, "pulseTable", "$sender", "routingTable");
        command.execute();

        PulseTable editedPulseTable = PulseTable.deserialize(sensor.getVariableValue("$pulseTable"));
        editedPulseTable.tickAllNeighbours();

        assertEquals(1,editedPulseTable.getDeadNeighbours().size());
        assertEquals("2", editedPulseTable.getDeadNeighbours().get(0));
    }

    @Test
    void registerPulseForConfig_RelayNode(){
        sensor.putVariable("sender", "R1");

        Command_REGISTERPULSEFORCONFIG command = new Command_REGISTERPULSEFORCONFIG(sensor, "pulseTable", "$sender", "routingTable");
        command.execute();

        PulseTable editedPulseTable = PulseTable.deserialize(sensor.getVariableValue("$pulseTable"));
        editedPulseTable.tickAllNeighbours();

        assertEquals(0,editedPulseTable.getDeadNeighbours().size());
    }

    @Test
    void registerPulseForConfig_UnknownNodeId(){
        sensor.putVariable("sender", "unknownId");

        Command_REGISTERPULSEFORCONFIG command = new Command_REGISTERPULSEFORCONFIG(sensor, "pulseTable", "$sender", "routingTable");

        assertThrows(RuntimeException.class, () -> command.execute());
    }

}