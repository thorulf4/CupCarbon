package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_SETLASTSENDERFORIDTest {

    private TestableSensorNode sensor;

    @BeforeEach
    void beforeEach(){
        sensor = new TestableSensorNode(1);

        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(100, "3", new DataPackage("m1", "R1", "hello"));
        messageTable.addMessage(100, "4", new DataPackage("m2", "R1", "hello"));

        sensor.putVariable("messageTable", messageTable.serialize());
    }

    @Test
    void execute() {
        DataPackage dataPackage = new DataPackage("m1", "R1", "hello");
        sensor.putVariable("data", dataPackage.serialize());
        sensor.putVariable("sender", "5");

        Command command = new Command_SETLASTSENDERFORID(sensor, "messageTable", "$data", "$sender");
        command.execute();

        MessageTable messageTable = MessageTable.deserialize(sensor.getVariableValue("$messageTable"));

        assertEquals("5", messageTable.getSender("m1"));
        assertEquals("4", messageTable.getSender("m2"));
    }

    @Test
    void execute_WithIdNotInTable() {
        DataPackage dataPackage = new DataPackage("m21435", "R1", "hello");
        sensor.putVariable("data", dataPackage.serialize());
        sensor.putVariable("sender", "5");

        Command command = new Command_SETLASTSENDERFORID(sensor, "messageTable", "$data", "$sender");

        assertThrows(RuntimeException.class, () -> command.execute());
    }
}