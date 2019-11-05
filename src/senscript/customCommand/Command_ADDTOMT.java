package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.backupRouting.MessageTable;



public class Command_ADDTOMT extends Command {

    private String mtVar;
    private DataPackage input;
    private String senderID;

    public Command_ADDTOMT(ISensorNode sensor, String mtVar, String input, String senderID){
        this.mtVar = mtVar;
        this.sensor = sensor;
        this.input = DataPackage.deserialize(input);
        this.senderID = senderID;

    }

    @Override
    public double execute(){
        MessageTable MT = MessageTable.deserialize("$"+mtVar);
        MT.addMessage(input.getMessageID(), senderID, input);
        sensor.putVariable(mtVar, MT.serialize());
        return 0;

    }
}
