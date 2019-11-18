package senscript.customCommand;

import d504.*;
import d504.backupRouting.MessageTable;
import d504.exceptions.MessageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETSENDERFROMMESSAGETABLETest {

    private ISensorNode sensor;
    private Command_GETSENDERFROMMESSAGETABLE command;
    private String messageTableVariable;
    private String ackMessageVariable;
    private String senderIdOutputVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensor = new TestableSensorNode(1);

        messageTableVariable = "messageTableVariable";
        ackMessageVariable = "ackMessageVariable";
        senderIdOutputVariable = "senderIdOutputVariable";
    }

    void createCommand(MessageTable messageTable, TypedPackage ackMessage){
        sensor.putVariable(messageTableVariable, messageTable.serialize());
        sensor.putVariable(ackMessageVariable, ackMessage.serialize());
        command = new Command_GETSENDERFROMMESSAGETABLE(sensor, "$" + ackMessageVariable, "$" + messageTableVariable, senderIdOutputVariable);
    }

    @Test
    void returnsCorrectSenderId_WithOneEntryInTable(){
        MessageTable messageTable = new MessageTable();
        DataPackage data = new DataPackage("A", "data");
        messageTable.addMessage(6000, "2", data);
        AckMessage ackMessage = new AckMessage(data.getMessageID());

        createCommand(messageTable, new TypedPackage(PackageType.Ack, "1", ackMessage.serialize()));
        command.execute();
        String returnedSenderId = sensor.getVariableValue("$" + senderIdOutputVariable);

        assertEquals("2", returnedSenderId);
    }

    @Test
    void returnsCorrectSenderId_WithMultipleEntriesInTable(){
        MessageTable messageTable = new MessageTable();
        DataPackage data1 = new DataPackage("A", "data");
        DataPackage data2 = new DataPackage("A", "data");
        DataPackage data3 = new DataPackage("A", "data");
        messageTable.addMessage(6000, "2", data1);
        messageTable.addMessage(6000, "3", data2);
        messageTable.addMessage(6000, "4", data3);
        AckMessage ackMessage = new AckMessage(data2.getMessageID());


        createCommand(messageTable, new TypedPackage(PackageType.Ack, "1", ackMessage.serialize()));
        command.execute();
        String returnedSenderId = sensor.getVariableValue("$" + senderIdOutputVariable);

        assertEquals("3", returnedSenderId);
    }

    @Test
    void noEntryInMessageTable(){
        MessageTable messageTable = new MessageTable();
        DataPackage data = new DataPackage("A", "data");

        AckMessage ackMessage = new AckMessage(data.getMessageID());
        createCommand(messageTable, new TypedPackage(PackageType.Ack, "1", ackMessage.serialize()));

        assertThrows(MessageNotFoundException.class, () -> command.execute());
    }
}