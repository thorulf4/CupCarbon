package senscript.customCommand;

import d504.AckMessage;
import d504.backupRouting.MessageTable;
import d504.utils.Serialize;
import device.SensorNode;
import senscript.Command;

import java.util.List;

public class Command_GETRECEIVERS extends Command {

    private String messageTableVariable;
    private String ackPacketVariable;
    private String receiversOutputVariable;
    private String hasElementsOutputVariable;

    public Command_GETRECEIVERS(SensorNode sensorNode, String messageTableVariable, String ackPacketVariable, String receiversOutputVariable, String hasElementsOutputVariable) {
        this.sensor = sensorNode;
        this.messageTableVariable = messageTableVariable;
        this.ackPacketVariable = ackPacketVariable;
        this.receiversOutputVariable = receiversOutputVariable;
        this.hasElementsOutputVariable = hasElementsOutputVariable;
    }

    @Override
    public double execute() {
        String serializedMessageTable = getVariableValue("$" + messageTableVariable);
        String serializedAckPacket = getVariableValue(ackPacketVariable);

        MessageTable messageTable = MessageTable.deserialize(serializedMessageTable);
        AckMessage ackMessage = AckMessage.deserialize(serializedAckPacket);

        String messageId = ackMessage.getMessageId();

        List<String> receivers = messageTable.getReceivers(messageId);

        putVariableValue(receiversOutputVariable, Serialize.serialize(receivers));
        putVariableValue(hasElementsOutputVariable, Boolean.toString(receivers.size() != 0));
        return 0;
    }

    public String getVariableValue(String variable){
        return sensor.getScript().getVariableValue(variable);
    }

    public void putVariableValue(String variable, String value){
        sensor.getScript().putVariable(variable, value);
    }


}
