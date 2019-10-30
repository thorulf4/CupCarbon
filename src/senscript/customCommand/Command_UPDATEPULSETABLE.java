package senscript.customCommand;

import d504.pulseTable.PulseTable;
import device.SensorNode;
import senscript.Command;

public class Command_UPDATEPULSETABLE extends Command {

    private SensorNode sensor;
    private String pulseTableVariable;

    public Command_UPDATEPULSETABLE(SensorNode sensor, String pulseTableVariable) {
        this.sensor = sensor;
        this.pulseTableVariable = pulseTableVariable;
    }

    @Override
    public double execute() {
        String serializedPulseTable = sensor.getScript().getVariableValue("$" + pulseTableVariable);
        PulseTable pulseTable = PulseTable.deserialize(serializedPulseTable);

        pulseTable.tickAllNeighbours();

        sensor.getScript().putVariable(pulseTableVariable, pulseTable.serialize());
    }
}
