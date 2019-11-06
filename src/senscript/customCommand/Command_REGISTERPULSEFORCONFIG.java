package senscript.customCommand;

import d504.ISensorNode;
import d504.pulseTable.PulseTable;
import d504.routingTable.RoutingTable;

public class Command_REGISTERPULSEFORCONFIG extends Command {

    private String pulseTableVariable;
    private String neighbourIdVariable;
    private String routingTableVariable;

    public Command_REGISTERPULSEFORCONFIG(ISensorNode sensorNode, String pulseTableVariable, String neighbourIdVariable, String routingTableVariable) {
        this.sensor = sensorNode;
        this.pulseTableVariable = pulseTableVariable;
        this.neighbourIdVariable = neighbourIdVariable;
        this.routingTableVariable = routingTableVariable;
    }

    @Override
    public double execute() {
        String neighbourId = sensor.getVariableValue(neighbourIdVariable);
        RoutingTable routingTable = getRoutingTable();
        PulseTable pulseTable = getPulseTable();

        if(!isNodeRelay(neighbourId, routingTable)){
            pulseTable.pulseNeighbour(neighbourId);
            sensor.putVariable(pulseTableVariable, pulseTable.serialize());
        }

        return 0;
    }

    private boolean isNodeRelay(String neighbourId, RoutingTable routingTable) {
        return routingTable.getQuickestRoutesForAllRelays().stream()
                .anyMatch(rrc -> rrc.getRelayId().equals(neighbourId));
    }

    private PulseTable getPulseTable() {
        String serializedPulseTable = sensor.getVariableValue("$"+pulseTableVariable);
        return PulseTable.deserialize(serializedPulseTable);
    }

    private RoutingTable getRoutingTable() {
        String serialized = sensor.getVariableValue(routingTableVariable);
        return RoutingTable.deserialize(serialized);
    }
}
