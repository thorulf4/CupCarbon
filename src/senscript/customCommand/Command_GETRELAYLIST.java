package senscript.customCommand;

import d504.ISensorNode;
import d504.routingTable.RoutingTable;
import d504.utils.Serialize;

import java.util.List;
import java.util.Set;

public class Command_GETRELAYLIST extends Command {

    private String routingTableVariable;
    private String relayListOutput;

    public Command_GETRELAYLIST(ISensorNode sensor, String routingTableVariable, String relayListOutput) {
        this.sensor = sensor;
        this.routingTableVariable = routingTableVariable;
        this.relayListOutput = relayListOutput;
    }

    @Override
    public double execute() {
        RoutingTable routingTable = RoutingTable.deserialize(sensor.getVariableValue("$"+routingTableVariable));

        List<String> relays = routingTable.getRelayIds();

        sensor.putVariable(relayListOutput, Serialize.serialize(relays));
        return 0;
    }
}
