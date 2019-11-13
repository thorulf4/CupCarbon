package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_TICKEXPIRATIONTIMERSTest {

    @Test
    void execute_IsNotExpired01() {
        TestableSensorNode sensor = new TestableSensorNode(1);
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage("m1", 3600, "n1", new DataPackage("m1", "R1", "Hello"));

        sensor.putVariable("messageTable", messageTable.serialize());

        Command_TICKEXPIRATIONTIMERS command = new Command_TICKEXPIRATIONTIMERS(sensor, "messageTable");
        command.execute();

        MessageTable editedMessageTable = MessageTable.deserialize(sensor.getVariableValue("$messageTable"));

        assertTrue(editedMessageTable.isMessagePresent("m1"));

    }

    @Test
    void execute_IsNotExpired02() {
        TestableSensorNode sensor = new TestableSensorNode(1);
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage("m1", 3600, "n1", new DataPackage("m1", "R1", "Hello"));

        sensor.putVariable("messageTable", messageTable.serialize());

        sensor.addTime(1000);

        Command_TICKEXPIRATIONTIMERS command = new Command_TICKEXPIRATIONTIMERS(sensor, "messageTable");
        command.execute();

        MessageTable editedMessageTable = MessageTable.deserialize(sensor.getVariableValue("$messageTable"));

        assertTrue(editedMessageTable.isMessagePresent("m1"));

    }

    @Test
    void execute_IsExpired() {
        TestableSensorNode sensor = new TestableSensorNode(1);
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage("m1", 3600, "n1", new DataPackage("m1", "R1", "Hello"));

        sensor.putVariable("messageTable", messageTable.serialize());

        sensor.addTime(4000);

        Command_TICKEXPIRATIONTIMERS command = new Command_TICKEXPIRATIONTIMERS(sensor, "messageTable");
        command.execute();

        MessageTable editedMessageTable = MessageTable.deserialize(sensor.getVariableValue("$messageTable"));

        assertFalse(editedMessageTable.isMessagePresent("m1"));

    }
}