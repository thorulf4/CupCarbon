package senscript.customCommand;

import d504.DataPackage;
import device.SensorNode;
import senscript.Command;

public class Command_GETDATA extends Command {

    String input="";
    String output="";

    public Command_GETDATA(SensorNode node, String input, String output){
        this.sensor = node;
        this.input = input;
        this.output = output;
    }

    @Override
    public double execute(){
        String data = sensor.getScript().getVariableValue(input);
        DataPackage message = DataPackage.deserialize(data);
        sensor.getScript().putVariable(output,message.getData());
        return 0;
    }
}
