package senscript.customCommand;

import d504.*;
import d504.routingTable.RoutingTable;



import java.util.Set;

public class Command_CREATECONFIG extends Command {

    private String routingTableVariable;
    private String outputPacketVariable;

    public Command_CREATECONFIG(ISensorNode sensorNode, String routingTableVariable, String outputPacketVariable) {
        this.sensor = sensorNode;
        this.routingTableVariable = routingTableVariable;
        this.outputPacketVariable = outputPacketVariable;
    }

    @Override
    public double execute() {
        String routingTableData = sensor.getVariableValue("$"+routingTableVariable);

        RoutingTable routingTable = RoutingTable.deserialize(routingTableData);

        Set<RelayRouteCost> relayTable = routingTable.getQuickestRoutesForAllRelays();

        ConfigPackage configPackage = new ConfigPackage(relayTable, String.valueOf(sensor.getId()));
        TypedPackage typedPackage = new TypedPackage(PackageType.Config, Integer.toString(sensor.getId()),configPackage.serialize());

        sensor.putVariable(outputPacketVariable, typedPackage.serialize());
        return 0;
    }
}
