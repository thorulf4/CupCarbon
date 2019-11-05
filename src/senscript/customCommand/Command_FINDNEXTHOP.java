package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.routingTable.RoutingTable;



public class Command_FINDNEXTHOP extends Command {

    private String routingTableVariable;
    private String outputNodeVariable;
    private String dataPacketVariable;

    public Command_FINDNEXTHOP(ISensorNode sensorNode, String routingTableVariable, String dataPacketVariable, String outputNodeVariable) {
        this.sensor = sensorNode;
        this.routingTableVariable = routingTableVariable;
        this.outputNodeVariable = outputNodeVariable;
        this.dataPacketVariable = dataPacketVariable;
    }

    @Override
    public double execute() {
        String routingTableData = sensor.getVariableValue("$"+routingTableVariable);
        String dataPacketData = sensor.getVariableValue(dataPacketVariable);

        RoutingTable routingTable = RoutingTable.deserialize(routingTableData);

        DataPackage dataPackage = DataPackage.deserialize(dataPacketData);

        String nodeId = routingTable.getQuickestRouteForRelay(dataPackage.getTargetRelay()).getNodeId();

        sensor.putVariable(outputNodeVariable, nodeId);
        return 0;
    }
}
