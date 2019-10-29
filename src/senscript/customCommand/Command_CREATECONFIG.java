package senscript.customCommand;

import d504.ConfigPackage;
import d504.PackageType;
import d504.RelayRouteCost;
import d504.TypedPackage;
import d504.routingTable.RoutingTable;
import device.SensorNode;
import senscript.Command;

import java.util.Set;

public class Command_CREATECONFIG extends Command {

    private String routingTableVariable;
    private String outputPacketVariable;

    public Command_CREATECONFIG(SensorNode sensorNode, String routingTableVariable, String outputPacketVariable) {
        this.sensor = sensorNode;
        this.routingTableVariable = routingTableVariable;
        this.outputPacketVariable = outputPacketVariable;
    }

    @Override
    public double execute() {
        String routingTableData = sensor.getScript().getVariableValue("$"+routingTableVariable);

        RoutingTable routingTable = RoutingTable.deserialize(routingTableData);

        Set<RelayRouteCost> relayTable = routingTable.getQuickestRoutesForAllRelays();

        ConfigPackage configPackage = new ConfigPackage(relayTable, String.valueOf(sensor.getId()));
        TypedPackage typedPackage = new TypedPackage(PackageType.Config, configPackage.serialize());

        sensor.getScript().putVariable(outputPacketVariable, typedPackage.serialize());
        return 0;
    }
}
