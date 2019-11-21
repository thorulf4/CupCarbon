package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.NodeCost;
import d504.backupRouting.MessageTable;
import d504.routingTable.RoutingTable;



import java.util.List;
import java.util.SortedSet;

public class Command_GETNEXTHOP extends Command {

    private String routingTableVariable;
    private String messageTableVariable;
    private String dataPacketVariable;
    private String nextHopOutputVariable;

    public Command_GETNEXTHOP(ISensorNode sensorNode, String routingTableVariable, String messageTableVariable, String dataPacketVariable, String nextHopOutputVariable) {
        this.sensor = sensorNode;
        this.routingTableVariable = routingTableVariable;
        this.messageTableVariable = messageTableVariable;
        this.dataPacketVariable = dataPacketVariable;
        this.nextHopOutputVariable = nextHopOutputVariable;
    }

    @Override
    public double execute() {
        RoutingTable routingTable = deserializeRoutingTable();
        MessageTable messageTable = deserializeMessageTable();
        DataPackage dataPackage = deserializeDataPacket();

        List<String> alreadyTried = messageTable.getReceivers(dataPackage.getMessageID());

        SortedSet<NodeCost> routes = routingTable.getSortedRouteListToRelay(dataPackage.getTargetRelay());

        String nextNode = getFirstNotInListOrUseBackup(alreadyTried, routes, messageTable.getSender(dataPackage.getMessageID()));
        messageTable.addReceiver(dataPackage.getMessageID(), nextNode);

        putVariableValue(nextHopOutputVariable, nextNode);
        putVariableValue(messageTableVariable, messageTable.serialize());

        return 0;
    }

    private String getFirstNotInListOrUseBackup(List<String> alreadyTried, SortedSet<NodeCost> routes, String backupRoute) {
        for (NodeCost route : routes) {
            if(!alreadyTried.contains(route.getNodeId())){
                return route.getNodeId();
            }
        }

        return backupRoute;
    }

    private DataPackage deserializeDataPacket() {
        return DataPackage.deserialize(getVariableValue(dataPacketVariable));
    }

    private RoutingTable deserializeRoutingTable() {
        return RoutingTable.deserialize(getVariableValue("$" + routingTableVariable));
    }

    private MessageTable deserializeMessageTable() {
        return MessageTable.deserialize(getVariableValue("$" + messageTableVariable));
    }

    public String getVariableValue(String variable){
        return sensor.getVariableValue(variable);
    }

    public void putVariableValue(String variable, String value){
        sensor.putVariable(variable, value);
    }
}
