package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.backupRouting.MessageTable;



public class Command_ISINMT extends Command {

    private String mtVar;
    private DataPackage message;
    private String output;
    private boolean isPresent;


    public Command_ISINMT(ISensorNode sensor, String mtVar, String message, String output){
        this.sensor=sensor;
        this.mtVar=mtVar;
        this.message = DataPackage.deserialize(message);
        this.output=output;

    }

    @Override
    public double execute(){
        MessageTable MT = MessageTable.deserialize("$"+mtVar);
        isPresent = MT.isMessagePresent(message.getMessageID());
        sensor.putVariable(output, Boolean.toString(isPresent));
        return 0;
    }
}
