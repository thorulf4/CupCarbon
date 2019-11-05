package senscript.customCommand;

import d504.ISensorNode;
import d504.pulseTable.PulseTable;



public class Command_UPDATEPULSETABLE extends Command {

    private ISensorNode sensor;
    private String pulseTableVariable;

    public Command_UPDATEPULSETABLE(ISensorNode sensor, String pulseTableVariable) {
        this.sensor = sensor;
        this.pulseTableVariable = pulseTableVariable;
    }

    @Override
    public double execute() {
        String serializedPulseTable = sensor.getVariableValue("$" + pulseTableVariable);
        PulseTable pulseTable = PulseTable.deserialize(serializedPulseTable);

        pulseTable.tickAllNeighbours();

        sensor.putVariable(pulseTableVariable, pulseTable.serialize());
        return 0;
    }
}
