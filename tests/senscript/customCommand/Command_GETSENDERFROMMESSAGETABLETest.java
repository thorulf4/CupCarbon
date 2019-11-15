package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import d504.exceptions.MessageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETSENDERFROMMESSAGETABLETest {

    private ISensorNode sensor;
    private Command_GETSENDERFROMMESSAGETABLE command;
    private String messageTableVariable;
    private String messageIdVariable;
    private String senderIdOutputVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensor = new TestableSensorNode(1);

        messageTableVariable = "messageTableVariable";
        messageIdVariable = "messageIdVariable";
        senderIdOutputVariable = "senderIdOutputVariable";
    }

    void createCommand(MessageTable messageTable, String messageId){
        sensor.putVariable(messageTableVariable, messageTable.serialize());
        sensor.putVariable(messageIdVariable, messageId);
        command = new Command_GETSENDERFROMMESSAGETABLE(sensor, "$" + messageIdVariable, "$" + messageTableVariable, senderIdOutputVariable);
    }

    @Test
    void returnsCorrectSenderId_WithOneEntryInTable(){
        MessageTable messageTable = new MessageTable();
        DataPackage data = new DataPackage("A", "data");
        messageTable.addMessage(6000, "2", data);

        createCommand(messageTable, data.getMessageID());
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

        createCommand(messageTable, data2.getMessageID());
        command.execute();
        String returnedSenderId = sensor.getVariableValue("$" + senderIdOutputVariable);

        assertEquals("3", returnedSenderId);
    }

    @Test
    void noEntryInMessageTable(){
        MessageTable messageTable = new MessageTable();
        DataPackage data = new DataPackage("A", "data");

        createCommand(messageTable, data.getMessageID());

        assertThrows(MessageNotFoundException.class, () -> command.execute());
    }
}