package senscript.customCommand;

import d504.ISensorNode;
import d504.PulseMessage;
import d504.routingTable.RoutingTable;



public class Command_ISINRT extends Command {

    private String routingTableVariable;
    private String pulsePacketVariable;
    private String isInRoutingTableVariable;

    public Command_ISINRT(ISensorNode sensorNode, String routingTableVariable, String pulsePacketVariable, String isInRoutingTableVariable) {
        this.sensor = sensorNode;
        this.routingTableVariable = routingTableVariable;
        this.pulsePacketVariable = pulsePacketVariable;
        this.isInRoutingTableVariable = isInRoutingTableVariable;
    }

    @Override
    public double execute() {
        String routingTableData = sensor.getVariableValue("$"+routingTableVariable);
        String pulsePacketData = sensor.getVariableValue(pulsePacketVariable);

        RoutingTable routingTable = RoutingTable.deserialize(routingTableData);
        PulseMessage pulseMessage = PulseMessage.deserialize(pulsePacketData);

        boolean isNodeInTable = routingTable.isNodeInRoutingTable(pulseMessage.senderId);

        sensor.putVariable(isInRoutingTableVariable, Boolean.toString(isNodeInTable));
        return 0;
    }
}
