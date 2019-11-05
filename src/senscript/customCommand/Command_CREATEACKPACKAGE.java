package senscript.customCommand;

import d504.AckMessage;
import d504.ISensorNode;
import d504.PackageType;
import d504.TypedPackage;
import d504.utils.Serialize;



public class Command_CREATEACKPACKAGE extends Command {

    private String input;
    private String output;


    public Command_CREATEACKPACKAGE(ISensorNode sensor, String input, String output){
        this.sensor = sensor;
        this.input = input;
        this.output = output;
    }


    @Override
    public double execute(){
        String messageID = Serialize.nextElement(input);
        AckMessage ackMessage = new AckMessage(Integer.toString(sensor.getId()),messageID);
        TypedPackage typedPackage = new TypedPackage(PackageType.Ack, Integer.toString(sensor.getId()), ackMessage.serialize());
        sensor.putVariable(output,typedPackage.serialize());
        return 0;
    }

}
