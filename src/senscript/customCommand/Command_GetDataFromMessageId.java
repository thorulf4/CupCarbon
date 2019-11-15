package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.backupRouting.MessageTable;

public class Command_GetDataFromMessageId extends Command {

    private String messageTableVariable;
    private String timedOutMessagesVariable;
    private String timedOutDataVariable;

    public Command_GetDataFromMessageId(ISensorNode sensorNode, String messageTableVariable, String timedOutMessagesVariable, String timedOutDataVariable) {
        this.sensor = sensorNode;
        this.messageTableVariable = messageTableVariable;
        this.timedOutMessagesVariable = timedOutMessagesVariable;
        this.timedOutDataVariable = timedOutDataVariable;
    }

    @Override
    public double execute() {
        MessageTable messageTable = getMessageTable();
        String timedOutMessageId = getTimedOutMessageId();

        DataPackage timedOutData = messageTable.getDataPackage(timedOutMessageId);
        sensor.putVariable(timedOutDataVariable, timedOutData.serialize());

        return 0;
    }

    private String getTimedOutMessageId() {
        return sensor.getVariableValue(timedOutMessagesVariable);
    }

    private MessageTable getMessageTable() {
        String serializedTable = sensor.getVariableValue(messageTableVariable);
        return MessageTable.deserialize(serializedTable);
    }
}
