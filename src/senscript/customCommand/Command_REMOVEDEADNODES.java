package senscript.customCommand;

import d504.RelayRouteCost;
import d504.pulseTable.PulseTable;
import d504.routingTable.RoutingTable;
import device.SensorNode;
import senscript.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Command_REMOVEDEADNODES extends Command {

    private SensorNode sensor;
    private String pulseTableVariable;
    private String routingTableVariable;
    private String hasQuickestRoutesChangedVariable;

    public Command_REMOVEDEADNODES(SensorNode sensor, String pulseTableVariable, String routingTableVariable, String hasQuickestRoutesChangedVariable) {
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

        boolean hasQuickestRoutesChanged = oldRoutes.equals(routingTable.getQuickestRoutesForAllRelays());
        sensor.getScript().putVariable(hasQuickestRoutesChangedVariable, Boolean.toString(hasQuickestRoutesChanged));

        return 0;
    }

    private RoutingTable getRoutingTable() {
        String serializedRoutingTable = sensor.getScript().getVariableValue("$" + routingTableVariable);
        return RoutingTable.deserialize(serializedRoutingTable);
    }

    private PulseTable getPulseTable() {
        String serializedPulseTable = sensor.getScript().getVariableValue("$" + pulseTableVariable);
        return PulseTable.deserialize(serializedPulseTable);
    }
}
