package senscript.customCommand;

import d504.ISensorNode;
import d504.NodeCost;
import d504.RelayRouteCost;
import d504.routingTable.RelayRoutes;
import d504.routingTable.RoutingTable;

import java.util.*;

public class Command_UPDATEROUTINGTABLELOWBATTERY extends Command{
    private String routingTableVariable;
    private String shouldCreateConfigVariable;

    public Command_UPDATEROUTINGTABLELOWBATTERY(ISensorNode sensorNode, String routingTableVariable, String shouldCreateConfigVariable) {
        this.sensor = sensorNode;
        this.routingTableVariable = routingTableVariable;
        this.shouldCreateConfigVariable = shouldCreateConfigVariable;
    }

    @Override
    public double execute() {
        RoutingTable routingTable = getRoutingTable();
        Set<RelayRouteCost> oldRoutes = routingTable.getQuickestRoutesForAllRelays();

        for(RelayRoutes relayRoutes : routingTable.getRoutingTable()){
            int max = relayRoutes.getMaxCost();
            int min = relayRoutes.getMinCost();

            relayRoutes.setModifier(max - min);
        }

        boolean shouldCreateConfig = !oldRoutes.equals(routingTable.getQuickestRoutesForAllRelays());

        sensor.putVariable(routingTableVariable, routingTable.serialize());
        sensor.putVariable(shouldCreateConfigVariable, Boolean.toString(shouldCreateConfig));

        return 0;
    }

    private RoutingTable getRoutingTable() {
        String serializedTable = sensor.getVariableValue("$" + routingTableVariable);
        return RoutingTable.deserialize(serializedTable);
    }
}
