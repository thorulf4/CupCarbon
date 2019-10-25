package senscript.customCommand;

import device.SensorNode;
import senscript.Command;

public class Command_DECIPHER extends Command {

    private String inputRead;
    private SensorNode sensorNode;

    public  Command_DECIPHER(SensorNode sensorNode, String inputRead){
        this.sensorNode = sensorNode;
        this.inputRead = inputRead;
    }

    @Override
    public double execute() {
        inputRead =  sensorNode.getScript().getVariableValue(inputRead);
      char prefixIdentifier =  inputRead.charAt(0);
      String s = String.valueOf(prefixIdentifier);
      sensorNode.getScript().putVariable("prefix",s);

      StringBuilder stringBuilder = new StringBuilder(inputRead);
      stringBuilder.deleteCharAt(0);
      String inputWithoutPrefix = stringBuilder.toString();
      sensorNode.getScript().putVariable("package", inputWithoutPrefix);
        return 0;
    }
}

//enum Package {CONFIG, DATA, PULS}
