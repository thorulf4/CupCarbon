package senscript.customCommand;

import d504.AckMessage;
import d504.ISensorNode;
import d504.backupRouting.MessageTable;

public class Command_DECREASECONGASTEPRELAY extends Command {
    private String serializedMessageTable;
    private String serializedAckMessage;
    private String outputShouldResend;

    public Command_DECREASECONGASTEPRELAY(ISensorNode sensorNode, String serializedMessageTable, String serializedAckMessage, String outputShouldResend) {
        this.sensor = sensorNode;
        this.serializedMessageTable = serializedMessageTable;
        this.serializedAckMessage = serializedAckMessage;
        this.outputShouldResend = outputShouldResend;
    }

    @Override
    public double execute() {
        MessageTable messageTable = getMessageTable();
        AckMessage ackMessage = getAckMessage();

        boolean shouldResend = messageTable.isLastActiveInCongaLine(ackMessage.getMessageId());
        messageTable.decreaseCongaStepForRelayAck(ackMessage.getMessageId());

        sensor.putVariable(outputShouldResend, Boolean.toString(shouldResend));
        sensor.putVariable(serializedMessageTable, messageTable.serialize());
        return 0;
    }

    private AckMessage getAckMessage() {
        String serializedAck = sensor.getVariableValue(serializedAckMessage);
        return AckMessage.deserialize(serializedAck);
    }

    private MessageTable getMessageTable() {
        String serializedMessageTable = sensor.getVariableValue("$" + this.serializedMessageTable);
        return MessageTable.deserialize(serializedMessageTable);
    }
}
