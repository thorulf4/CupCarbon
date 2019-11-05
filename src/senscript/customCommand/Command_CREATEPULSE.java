package senscript.customCommand;

import d504.ISensorNode;
import d504.PackageType;
import d504.PulseMessage;
import d504.TypedPackage;



public class Command_CREATEPULSE extends Command {
    private String outputPacketVariable;

    public Command_CREATEPULSE(ISensorNode sensorNode, String outputPacketVariable) {
        this.sensor = sensorNode;
        this.outputPacketVariable = outputPacketVariable;
    }

    @Override
    public double execute() {
        String sensorId = "" + sensor.getId();

        PulseMessage pulseMessage = new PulseMessage(sensorId);
        TypedPackage packet = new TypedPackage(PackageType.Pulse, Integer.toString(sensor.getId()), pulseMessage.serialize());
        String packetData = packet.serialize();

        sensor.putVariable(outputPacketVariable, packetData);
        return 0;
    }
}
