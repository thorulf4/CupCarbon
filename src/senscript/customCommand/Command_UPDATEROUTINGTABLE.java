package senscript.customCommand;

import d504.ConfigPackage;
import d504.ISensorNode;
import d504.routingTable.RoutingTable;



public class Command_UPDATEROUTINGTABLE extends Command {

    private ISensorNode sensor;
    private String routingTableVariable;
    private String configPackageVariable;
    private String outputBool;

    public Command_UPDATEROUTINGTABLE(ISensorNode sensor, String routingTableVariable, String configPackageVariable, String outputBool) {
        this.sensor = sensor;
        this.routingTableVariable = routingTableVariable;
        this.configPackageVariable = configPackageVariable;
        this.outputBool = outputBool;
    }

    @Override
    public double execute() {
        RoutingTable routingTable = RoutingTable.deserialize(sensor.getVariableValue("$" + routingTableVariable));
        ConfigPackage configPackage = ConfigPackage.deserialize(sensor.getVariableValue(configPackageVariable));

        boolean hasChanged = routingTable.update(configPackage);

        sensor.putVariable(routingTableVariable, routingTable.serialize());
        sensor.putVariable(outputBool, Boolean.toString(hasChanged));

        return 0;
    }
}
