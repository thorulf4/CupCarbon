package senscript.customCommand;

import d504.ISensorNode;
import d504.PulseMessage;



public class Command_GETSENDER extends Command {

    private String packet;
    private String outputVariable;

    public Command_GETSENDER(ISensorNode sensorNode, String packet, String outputVariable) {
        this.sensor = sensorNode;

        this.packet = packet;
        this.outputVariable = outputVariable;
    }

    @Override
    public double execute() {
        String data = sensor.getVariableValue(packet);
        PulseMessage pulseMessage = PulseMessage.deserialize(data);

        sensor.putVariable( outputVariable, pulseMessage.senderId);

        return 0;
    }
}
