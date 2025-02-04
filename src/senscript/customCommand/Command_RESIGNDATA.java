package senscript.customCommand;

import d504.ISensorNode;
import d504.PackageType;
import d504.TypedPackage;



public class Command_RESIGNDATA extends Command {

    private String input;
    private String output;

    public Command_RESIGNDATA(ISensorNode sensor, String input, String output){
        this.sensor=sensor;
        this.input=input;
        this.output=output;
    }


    @Override
    public double execute(){
        String serializedData = sensor.getVariableValue(input);
        TypedPackage typedPackage = new TypedPackage(PackageType.Data, Integer.toString(sensor.getId()), serializedData);
        sensor.putVariable(output, typedPackage.serialize());
        return 0;
    }
}
