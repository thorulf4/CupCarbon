package senscript.customCommand;

import d504.AckMessage;
import d504.PackageType;
import d504.TypedPackage;
import d504.utils.Serialize;
import device.SensorNode;
import senscript.Command;

public class Command_CREATEACKPACKAGE extends Command {

    private String input;
    private String output;


    public Command_CREATEACKPACKAGE(SensorNode sensor, String input, String output){
        this.sensor = sensor;
        this.input = input;
        this.output = output;
    }


    @Override
    public double execute(){
        String messageID = Serialize.nextElement(input);
        AckMessage ackMessage = new AckMessage(Integer.toString(sensor.getId()),messageID);
        TypedPackage typedPackage = new TypedPackage(PackageType.Ack, Integer.toString(sensor.getId()), ackMessage.serialize());
        sensor.getScript().putVariable(output,typedPackage.serialize());
        return 0;
    }

}
