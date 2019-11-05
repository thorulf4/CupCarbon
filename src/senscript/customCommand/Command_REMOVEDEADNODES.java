package senscript.customCommand;

import d504.ISensorNode;
import d504.RelayRouteCost;
import d504.pulseTable.PulseTable;
import d504.routingTable.RoutingTable;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Command_REMOVEDEADNODES extends Command {

    private ISensorNode sensor;
    private String pulseTableVariable;
    private String routingTableVariable;
    private String hasQuickestRoutesChangedVariable;

    public Command_REMOVEDEADNODES(ISensorNode sensor, String pulseTableVariable, String routingTableVariable, String hasQuickestRoutesChangedVariable) {
        this.sensor = sensor;
        this.pulseTableVariable = pulseTableVariable;
        this.routingTableVariable = routingTableVariable;
        this.hasQuickestRoutesChangedVariable = hasQuickestRoutesChangedVariable;
    }

    @Override
    public double execute() {
        PulseTable pulseTable = getPulseTable();
        RoutingTable routingTable = getRoutingTable();

        Set<RelayRouteCost> oldRoutes = routingTable.getQuickestRoutesForAllRelays();

        List<String> deadNodes = pulseTable.getDeadNeighbours();
        deadNodes.forEach(routingTable::removeNode);
        pulseTable.removeDeadNeighbours();

        boolean hasQuickestRoutesChanged = !oldRoutes.equals(routingTable.getQuickestRoutesForAllRelays());
        sensor.putVariable(hasQuickestRoutesChangedVariable, Boolean.toString(hasQuickestRoutesChanged));
        sensor.putVariable(routingTableVariable, routingTable.serialize());

        return 0;
    }

    private RoutingTable getRoutingTable() {
        String serializedRoutingTable = sensor.getVariableValue("$" + routingTableVariable);
        return RoutingTable.deserialize(serializedRoutingTable);
    }

    private PulseTable getPulseTable() {
        String serializedPulseTable = sensor.getVariableValue("$" + pulseTableVariable);
        return PulseTable.deserialize(serializedPulseTable);
    }
}
