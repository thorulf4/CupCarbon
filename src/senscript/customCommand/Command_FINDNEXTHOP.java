package senscript.customCommand;

import d504.DataPackage;
import d504.routingTable.RoutingTable;
import device.SensorNode;
import senscript.Command;

public class Command_FINDNEXTHOP extends Command {

    private String routingTableVariable;
    private String outputNodeVariable;
    private String dataPacketVariable;

    public Command_FINDNEXTHOP(SensorNode sensorNode, String routingTableVariable, String dataPacketVariable, String outputNodeVariable) {
        this.sensor = sensorNode;
        this.routingTableVariable = routingTableVariable;
        this.outputNodeVariable = outputNodeVariable;
        this.dataPacketVariable = dataPacketVariable;
    }

    @Override
    public double execute() {
        String routingTableData = sensor.getScript().getVariableValue("$"+routingTableVariable);
        String dataPacketData = sensor.getScript().getVariableValue(dataPacketVariable);

        RoutingTable routingTable = RoutingTable.deserialize(routingTableData);

        DataPackage dataPackage = DataPackage.deserialize(dataPacketData);

        String nodeId = routingTable.getFastetsRoute(dataPackage.getTargetRelay()).getNodeId();

        sensor.getScript().putVariable(outputNodeVariable, nodeId);
        return 0;
    }
}
