package senscript.customCommand;

import d504.AckMessage;
import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_DECREASECONGASTEPTest {

    @Test
    void execute_withoutResend() {
        TestableSensorNode sensor = new TestableSensorNode(1);

        MessageTable messageTable = new MessageTable();

        messageTable.addMessage(3600,"n1", new DataPackage("m1", "R1", "hello"));

        AckMessage ackMessage = new AckMessage("m1");

        sensor.putVariable("MT", messageTable.serialize());
        sensor.putVariable("ack", ackMessage.serialize());

        Command_DECREASECONGASTEP command = new Command_DECREASECONGASTEP(sensor, "MT", "$ack", "shouldResend");
        command.execute();

        boolean shouldResend = Boolean.parseBoolean(sensor.getVariableValue("$shouldResend"));


        assertTrue(shouldResend);
    }

    @Test
    void execute_withResend() {
        TestableSensorNode sensor = new TestableSensorNode(1);

        MessageTable messageTable = new MessageTable();

        messageTable.addMessage(3600,"n1", new DataPackage("m1", "R1", "hello"));

        messageTable.decreaseCongaStep("m1");

        AckMessage ackMessage = new AckMessage("m1");

        sensor.putVariable("MT", messageTable.serialize());
        sensor.putVariable("ack", ackMessage.serialize());

        Command_DECREASECONGASTEP command = new Command_DECREASECONGASTEP(sensor, "MT", "$ack", "shouldResend");
        command.execute();

        boolean shouldResend = Boolean.parseBoolean(sensor.getVariableValue("$shouldResend"));


        assertFalse(shouldResend);
    }
}