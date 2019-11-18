package senscript.customCommand;

import d504.AckMessage;
import d504.ISensorNode;
import d504.TypedPackage;
import d504.backupRouting.MessageTable;

public class Command_GETSENDERFROMMESSAGETABLE extends Command {

    private String messageTableVariable;
    private String ackPackageVariable;
    private String senderOutputVariable;

    public Command_GETSENDERFROMMESSAGETABLE(ISensorNode sensorNode, String ackPackageVariable, String messageTableVariable, String senderOutputVariable) {
        this.sensor = sensorNode;
        this.ackPackageVariable = ackPackageVariable;
        this.messageTableVariable = messageTableVariable;
        this.senderOutputVariable = senderOutputVariable;
    }

    @Override
    public double execute() {
        MessageTable messageTable = getMessageTable();
        String messageId = getMessageIdFromAckMessage();

        String senderId = messageTable.getSender(messageId);
        sensor.putVariable(senderOutputVariable, senderId);

        return 0;
    }

    private String getMessageIdFromAckMessage() {
        String serializedAckPackage = sensor.getVariableValue(ackPackageVariable);
        String serializedAck = TypedPackage.deserialize(serializedAckPackage).packageData;
        AckMessage ackMessage = AckMessage.deserialize(serializedAck);
        return ackMessage.getMessageId();
    }

    private MessageTable getMessageTable() {
        String serializedTable = sensor.getVariableValue(messageTableVariable);
        return MessageTable.deserialize(serializedTable);
    }
}
