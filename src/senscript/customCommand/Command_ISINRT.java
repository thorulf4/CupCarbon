package senscript.customCommand;

import d504.PulseMessage;
import d504.routingTable.RoutingTable;
import device.SensorNode;
import senscript.Command;

public class Command_ISINRT extends Command {

    private String routingTableVariable;
    private String pulsePacketVariable;
    private String outputShouldSendConfigVariable;

    public Command_ISINRT(SensorNode sensorNode, String routingTableVariable, String pulsePacketVariable, String outputShouldSendConfigVariable) {
        this.sensor = sensorNode;
        this.routingTableVariable = routingTableVariable;
        this.pulsePacketVariable = pulsePacketVariable;
        this.outputShouldSendConfigVariable = outputShouldSendConfigVariable;
    }

    @Override
    public double execute() {
        String routingTableData = sensor.getScript().getVariableValue("$"+routingTableVariable);
        String pulsePacketData = sensor.getScript().getVariableValue(pulsePacketVariable);

        RoutingTable routingTable = RoutingTable.deserialize(routingTableData);
        PulseMessage pulseMessage = PulseMessage.deserialize(pulsePacketData);

        boolean isNodeInTable = routingTable.isNodeInRoutingTable(pulseMessage.senderId);

        sensor.getScript().putVariable(outputShouldSendConfigVariable, isNodeInTable?"true":"false");
        return 0;
    }
}
