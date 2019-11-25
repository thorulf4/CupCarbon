package senscript.customCommand;

import d504.AckMessage;
import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_DECREASECONGASTEPRELAYTest {

    private TestableSensorNode sensor;
    private Command_DECREASECONGASTEPRELAY command;
    private String messageTableVariable;
    private String ackMessageVariable;
    private String shouldResendVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensor = new TestableSensorNode(1);

        messageTableVariable = "messageTableVariable";
        ackMessageVariable = "ackMessageVariable";
        shouldResendVariable = "shouldResendVariable";
    }

    void createCommand(MessageTable messageTable, AckMessage ackMessage){
        sensor.putVariable(messageTableVariable, messageTable.serialize());
        sensor.putVariable(ackMessageVariable, ackMessage.serialize());
        command = new Command_DECREASECONGASTEPRELAY(sensor, messageTableVariable,
                "$" + ackMessageVariable, shouldResendVariable);
    }

    @Test
    void newlyAddedMessageShouldTriggerResend(){
        DataPackage dataPackage = new DataPackage("A", "data");
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(1000, "1", dataPackage);
        AckMessage ackMessage = new AckMessage(dataPackage.getMessageID());

        createCommand(messageTable, ackMessage);
        command.execute();

        boolean shouldResend = Boolean.parseBoolean(sensor.getVariableValue("$" + shouldResendVariable));
        assertTrue(shouldResend);
    }

    @Test
    void shouldNotResendWhenHaveBeenDecreased(){
        DataPackage dataPackage = new DataPackage("A", "data");
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(1000, "1", dataPackage);
        AckMessage ackMessage = new AckMessage(dataPackage.getMessageID());

        messageTable.decreaseCongaStepForRelayAck(dataPackage.getMessageID());
        createCommand(messageTable, ackMessage);
        command.execute();

        boolean shouldResend = Boolean.parseBoolean(sensor.getVariableValue("$" + shouldResendVariable));
        assertFalse(shouldResend);
    }

}