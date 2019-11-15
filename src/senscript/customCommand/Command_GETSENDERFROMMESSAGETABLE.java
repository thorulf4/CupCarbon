package senscript.customCommand;

import d504.ISensorNode;
import d504.backupRouting.MessageTable;

public class Command_GETSENDERFROMMESSAGETABLE extends Command {

    private String messageTableVariable;
    private String messageIdVariable;
    private String senderOutputVariable;

    public Command_GETSENDERFROMMESSAGETABLE(ISensorNode sensorNode, String messageIdVariable, String messageTableVariable, String senderOutputVariable) {
        this.sensor = sensorNode;
        this.messageIdVariable = messageIdVariable;
        this.messageTableVariable = messageTableVariable;
        this.senderOutputVariable = senderOutputVariable;
    }

    @Override
    public double execute() {
        MessageTable messageTable = getMessageTable();
        String messageId = sensor.getVariableValue(messageIdVariable);

        String senderId = messageTable.getSender(messageId);
        sensor.putVariable(senderOutputVariable, senderId);

        return 0;
    }

    private MessageTable getMessageTable() {
        String serializedTable = sensor.getVariableValue(messageTableVariable);
        return MessageTable.deserialize(serializedTable);
    }
}
