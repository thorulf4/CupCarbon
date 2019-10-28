package senscript.customCommand;

import d504.ConfigPackage;
import d504.routingTable.RoutingTable;
import device.SensorNode;
import senscript.Command;

public class Command_UPDATEROUTINGTABLE extends Command {

    private SensorNode sensor;
    private String routingTableVariable;
    private String configPackageVariable;
    private String outputBool;

    public Command_UPDATEROUTINGTABLE(SensorNode sensor, String routingTableVariable, String configPackageVariable, String outputBool) {
        this.sensor = sensor;
        this.routingTableVariable = routingTableVariable;
        this.configPackageVariable = configPackageVariable;
        this.outputBool = outputBool;
    }

    @Override
    public double execute() {
        RoutingTable routingTable = RoutingTable.deserialize(sensor.getScript().getVariableValue("$" + routingTableVariable));
        ConfigPackage configPackage = ConfigPackage.deserialize(sensor.getScript().getVariableValue(configPackageVariable));

        boolean hasChanged = routingTable.update(configPackage);

        sensor.getScript().putVariable(routingTableVariable, routingTable.serialize());
        sensor.getScript().putVariable(outputBool, Boolean.toString(hasChanged));

        return 0;
    }
}
