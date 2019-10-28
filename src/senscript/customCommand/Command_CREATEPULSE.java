package senscript.customCommand;

import d504.PackageType;
import d504.PulseMessage;
import d504.TypedPackage;
import device.SensorNode;
import senscript.Command;

public class Command_CREATEPULSE extends Command {
    private String outputPacketVariable;

    public Command_CREATEPULSE(SensorNode sensorNode, String outputPacketVariable) {
        this.sensor = sensorNode;
        this.outputPacketVariable = outputPacketVariable;
    }

    @Override
    public double execute() {
        String sensorId = "" + sensor.getId();

        PulseMessage pulseMessage = new PulseMessage(sensorId);
        TypedPackage packet = new TypedPackage(PackageType.Pulse, pulseMessage.serialize());
        String packetData = packet.serialize();

        sensor.getScript().putVariable(outputPacketVariable, packetData);
        return 0;
    }
}
