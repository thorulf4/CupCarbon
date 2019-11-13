package senscript.customCommand;

import d504.AckMessage;
import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import d504.utils.Serialize;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETRECEIVERSTest {

    @Test
    void getReceiversTest1(){
        TestableSensorNode sensor = new TestableSensorNode(1);
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage("m1",3600, "3", new DataPackage("m1", "R1", "someData"));
        messageTable.addReceiver("m1", "2");
        messageTable.addReceiver("m1", "4");

        AckMessage ackMessage = new AckMessage("m1");

        sensor.putVariable("messageTable", messageTable.serialize());
        sensor.putVariable("ackPackage", ackMessage.serialize());

        Command_GETRECEIVERS command = new Command_GETRECEIVERS(sensor, "messageTable", "$ackPackage", "receiversListOutput", "listHasElementsOutput");
        command.execute();

        String[] receivers = sensor.getVariableValue("$receiversListOutput").split("&");
        boolean hasReceivers = Boolean.parseBoolean(sensor.getVariableValue("$listHasElementsOutput"));

        assertEquals(3, receivers.length, "Should contain all the receivers and the original sender");
        assertTrue(hasReceivers);
    }

    @Test
    void getReceiversTest2(){
        TestableSensorNode sensor = new TestableSensorNode(1);
        MessageTable messageTable = new MessageTable();

        AckMessage ackMessage = new AckMessage( "m13");

        sensor.putVariable("messageTable", messageTable.serialize());
        sensor.putVariable("ackPackage", ackMessage.serialize());

        Command_GETRECEIVERS command = new Command_GETRECEIVERS(sensor, "messageTable", "$ackPackage", "receiversListOutput", "listHasElementsOutput");
        command.execute();

        List<String> receivers = Serialize.deserializeStringList(sensor.getVariableValue("$receiversListOutput"));
        boolean hasReceivers = Boolean.parseBoolean(sensor.getVariableValue("$listHasElementsOutput"));

        assertEquals(0, receivers.size(), "Should contain all the receivers and the original sender");
        assertFalse(hasReceivers);
    }

}