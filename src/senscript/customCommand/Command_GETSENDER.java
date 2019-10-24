package senscript.customCommand;

import d504.PulseMessage;
import device.SensorNode;
import senscript.Command;

public class Command_GETSENDER extends Command {

    private String packet;
    private String outputVariable;

    public Command_GETSENDER(SensorNode sensorNode, String packet, String outputVariable) {
        this.sensor = sensorNode;

        this.packet = packet;
        this.outputVariable = outputVariable;
    }

    @Override
    public double execute() {
        String data = sensor.getScript().getVariableValue(packet);
        PulseMessage pulseMessage = PulseMessage.deserialize(data);

        sensor.getScript().putVariable( outputVariable, pulseMessage.senderId);

        return 0;
    }
}
