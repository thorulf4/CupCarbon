package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;


public class Command_GETDATA extends Command {

    String input="";
    String output="";

    public Command_GETDATA(ISensorNode node, String input, String output){
        this.sensor = node;
        this.input = input;
        this.output = output;
    }

    @Override
    public double execute(){
        String data = sensor.getVariableValue(input);
        if(!data.equals("")){
        DataPackage message = DataPackage.deserialize(data);
        sensor.putVariable(output,message.getData());
        return 0;
        }
        else{
            return -1;
        }
    }
}
