package senscript.customCommand;

import d504.TestableSensorNode;
import d504.pulseTable.PulseTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_REGISTERPULSETest {

    @Test
    void registerPulseTest1(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        PulseTable pulseTable = new PulseTable(2);

        sensor.putVariable("pulseTable", pulseTable.serialize());
        sensor.putVariable("pulseSender", "3");

        Command_REGISTERPULSE command = new Command_REGISTERPULSE(sensor, "pulseTable", "$pulseSender");
        command.execute();

        PulseTable editedPulseTable = PulseTable.deserialize(sensor.getVariableValue("$pulseTable"));
        editedPulseTable.tickAllNeighbours();
        editedPulseTable.tickAllNeighbours();


        assertEquals(1,editedPulseTable.getDeadNeighbours().size(), "Expected to have 1 dead neighbour in list");
        assertEquals("3",editedPulseTable.getDeadNeighbours().get(0), "Expected to have '3' as the dead neighbour in list");
    }

    @Test
    void registerPulseTest2(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        PulseTable pulseTable = new PulseTable(2);

        sensor.putVariable("pulseTable", pulseTable.serialize());
        sensor.putVariable("pulseSender", "5");

        Command_REGISTERPULSE command = new Command_REGISTERPULSE(sensor, "pulseTable", "$pulseSender");
        command.execute();

        command = new Command_REGISTERPULSE(sensor, "pulseTable", "$pulseSender");
        command.execute();

        PulseTable editedPulseTable = PulseTable.deserialize(sensor.getVariableValue("$pulseTable"));
        editedPulseTable.tickAllNeighbours();
        editedPulseTable.tickAllNeighbours();


        assertEquals(1,editedPulseTable.getDeadNeighbours().size(), "Expected to have 1 dead neighbour in list");
        assertEquals("5",editedPulseTable.getDeadNeighbours().get(0), "Expected to have '5' as the dead neighbour in list");
    }

    @Test
    void registerPulseInvalidArgumentTest1(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        PulseTable pulseTable = new PulseTable(2);

        sensor.putVariable("pulseTable", pulseTable.serialize());
        sensor.putVariable("pulseSender", "");

        Command_REGISTERPULSE command = new Command_REGISTERPULSE(sensor, "pulseTable", "$pulseSender");


        assertThrows(RuntimeException.class, () -> command.execute());
    }

    @Test
    void registerPulseInvalidArgumentTest2(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        PulseTable pulseTable = new PulseTable(2);

        sensor.putVariable("pulseTable", pulseTable.serialize());

        Command_REGISTERPULSE command = new Command_REGISTERPULSE(sensor, "pulseTable", "$pulseSender");


        assertThrows(RuntimeException.class, () -> command.execute());
    }

    @Test
    void registerPulseInvalidArgumentTest3(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        sensor.putVariable("pulseSender", "");

        Command_REGISTERPULSE command = new Command_REGISTERPULSE(sensor, "pulseTable", "$pulseSender");


        assertThrows(RuntimeException.class, () -> command.execute());
    }

}