package senscript.customCommand;

import d504.AckMessage;
import d504.ISensorNode;
import d504.backupRouting.MessageTable;
import d504.utils.Serialize;


import java.util.List;

public class Command_GETRECEIVERS extends Command {

    private String messageTableVariable;
    private String ackPacketVariable;
    private String receiversOutputVariable;
    private String hasElementsOutputVariable;

    public Command_GETRECEIVERS(ISensorNode sensorNode, String messageTableVariable, String ackPacketVariable, String receiversOutputVariable, String hasElementsOutputVariable) {
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

        if(messageTable.isMessagePresent(messageId)){
            List<String> receivers = messageTable.getReceivers(messageId);
            if(Integer.parseInt(messageTable.getSender(messageId)) != sensor.getId())
                receivers.add(messageTable.getSender(messageId));

            putVariableValue(receiversOutputVariable, Serialize.serialize(receivers));
            putVariableValue(hasElementsOutputVariable, Boolean.toString(receivers.size() != 0));
        }else{
            putVariableValue(receiversOutputVariable, "");
            putVariableValue(hasElementsOutputVariable, "False");
        }

        messageTable.removeMessage(messageId);
        putVariableValue(messageTableVariable, messageTable.serialize());


        return 0;
    }

    public String getVariableValue(String variable){
        return sensor.getVariableValue(variable);
    }

    public void putVariableValue(String variable, String value){
        sensor.putVariable(variable, value);
    }


}
