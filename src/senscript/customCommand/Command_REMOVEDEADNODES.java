package senscript.customCommand;

import d504.pulseTable.PulseTable;
import d504.routingTable.RoutingTable;
import device.SensorNode;
import senscript.Command;

import java.util.ArrayList;
import java.util.List;

public class Command_REMOVEDEADNODES extends Command {

    private SensorNode sensor;
    private String pulseTableVariable;
    private String routingTableVariable;

    public Command_REMOVEDEADNODES(SensorNode sensor, String pulseTableVariable, String routingTableVariable) {
        this.sensor = sensor;
        this.pulseTableVariable = pulseTableVariable;
        this.routingTableVariable = routingTableVariable;
    }

    @Override
    public double execute() {
        PulseTable pulseTable = getPulseTable();
        RoutingTable routingTable = getRoutingTable();

        List<String> deadNodes = pulseTable.getDeadNeighbours();
        deadNodes.forEach(routingTable::removeNode);
        pulseTable.removeDeadNeighbours();

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
