package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.backupRouting.MessageTable;



public class Command_ADDTOMT extends Command {

    private String MTVariable;
    private String input;
    private String senderIdVariable;

    public Command_ADDTOMT(ISensorNode sensor, String MTVariable, String input, String senderIdVariable){
        this.MTVariable = MTVariable;
        this.sensor = sensor;
        this.input = input;
        this.senderIdVariable = senderIdVariable;

    }

    @Override
    public double execute(){
        String senderID = sensor.getVariableValue(senderIdVariable);
        DataPackage dataPacket = DataPackage.deserialize(sensor.getVariableValue(input));
        MessageTable MT = MessageTable.deserialize(sensor.getVariableValue("$"+ MTVariable));

        MT.addMessage(dataPacket.getMessageID(), senderID, dataPacket);
        sensor.putVariable(MTVariable, MT.serialize());
        return 0;

    }
}
