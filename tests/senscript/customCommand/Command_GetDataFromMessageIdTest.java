package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import d504.exceptions.MessageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETDATAFROMMESSAGEIDTest {

    private Command_GETDATAFROMMESSAGEID command;
    private TestableSensorNode sensorNode;
    private String messageTableVariable;
    private String timedOutMessageVariable;
    private String timedOutDataVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensorNode = new TestableSensorNode(1);
        messageTableVariable = "messageTableVariable";
        timedOutDataVariable = "timedOutDataVariable";
        timedOutMessageVariable = "timedOutMessageVariable";
    }

    void createCommand(MessageTable messageTable, String timedOutMessage){
        sensorNode.putVariable(messageTableVariable, messageTable.serialize());
        sensorNode.putVariable(timedOutMessageVariable, timedOutMessage);
        command = new Command_GETDATAFROMMESSAGEID(sensorNode, "$"+messageTableVariable,
                "$"+timedOutMessageVariable, timedOutDataVariable);
    }

    @Test
    void returnsCorrectData(){
        MessageTable messageTable = new MessageTable();
        DataPackage dataPackage = new DataPackage("A", "data");
        messageTable.addMessage(6000, "1", dataPackage);

        createCommand(messageTable, dataPackage.getMessageID());
        command.execute();
        String returnedData = sensorNode.getVariableValue("$" + timedOutDataVariable);

        assertEquals(dataPackage.serialize(), returnedData);
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNotInTable(){
        MessageTable messageTable = new MessageTable();
        DataPackage dataPackage = new DataPackage("A", "data");

        createCommand(messageTable, dataPackage.getMessageID());

        assertThrows(MessageNotFoundException.class, () -> command.execute());
    }

}