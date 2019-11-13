package senscript.customCommand;

import d504.AckMessage;
import d504.ISensorNode;
import d504.backupRouting.MessageTable;

public class Command_DECREASECONGASTEP extends Command {

    private String serializedMessageTable;
    private String serializedAckMessage;
    private String outputShouldResend;

    public Command_DECREASECONGASTEP(ISensorNode sensorNode, String serializedMessageTable, String serializedAckMessage, String outputShouldResend) {
        this.sensor = sensorNode;
        this.serializedMessageTable = serializedMessageTable;
        this.serializedAckMessage = serializedAckMessage;
        this.outputShouldResend = outputShouldResend;
    }

    @Override
    public double execute() {
        MessageTable messageTable = MessageTable.deserialize(sensor.getVariableValue("$" + serializedMessageTable));
        AckMessage ackMessage = AckMessage.deserialize(sensor.getVariableValue(serializedAckMessage));

        messageTable.decreaseCongaStep(ackMessage.getMessageId());

        boolean shouldResend = messageTable.isCongaActive(ackMessage.getMessageId());
        sensor.putVariable(outputShouldResend, Boolean.toString(shouldResend));
        sensor.putVariable(serializedMessageTable, messageTable.serialize());
        return 0;
    }
}
