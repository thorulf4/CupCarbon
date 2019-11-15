package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.backupRouting.MessageTable;
import sun.plugin2.message.Message;

import javax.xml.crypto.Data;
import java.security.MessageDigest;

public class Command_SETLASTSENDERFORID extends Command {

    private String serializedMessageTableVariable;
    private String serializedDataPacketVariable;
    private String senderVariable;

    public Command_SETLASTSENDERFORID(ISensorNode sensor, String serializedMessageTableVariable, String serializedDataPacketVariable, String senderVariable) {
        this.sensor = sensor;
        this.serializedMessageTableVariable = serializedMessageTableVariable;
        this.serializedDataPacketVariable = serializedDataPacketVariable;
        this.senderVariable = senderVariable;
    }

    @Override
    public double execute() {
        String sender = sensor.getVariableValue(senderVariable);
        DataPackage dataPackage = DataPackage.deserialize(sensor.getVariableValue(serializedDataPacketVariable));
        MessageTable messageTable = MessageTable.deserialize(sensor.getVariableValue("$"+serializedMessageTableVariable));

        messageTable.setSender(dataPackage.getMessageID(), sender);

        sensor.putVariable(serializedMessageTableVariable, messageTable.serialize());
        return 0;
    }
}
