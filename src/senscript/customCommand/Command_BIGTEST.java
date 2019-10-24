package senscript.customCommand;

import device.SensorNode;
import senscript.Command;

public class Command_BIGTEST extends Command {

    String word;

    public Command_BIGTEST(SensorNode sensorNode, String arg1) {
        word = arg1;
    }

    @Override
    public double execute() {
        System.out.println("My test worked: " + word);
        return 0;
    }
}
