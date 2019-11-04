package senscript.customCommand;

import d504.PackageType;
import d504.TypedPackage;
import device.SensorNode;
import senscript.Command;

public class Command_RESIGNDATA extends Command {

    String input;
    String output;

    public Command_RESIGNDATA(SensorNode sensor, String input, String output){
        this.input=input;
        this.output=output;
    }


    @Override
    public double execute(){
        TypedPackage typedPackage = new TypedPackage(PackageType.Data, Integer.toString(sensor.getId()), input);
        sensor.getScript().putVariable(output, typedPackage.serialize());
        return 0;
    }
}
