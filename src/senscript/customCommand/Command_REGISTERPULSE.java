package senscript.customCommand;

import d504.pulseTable.PulseTable;
import device.SensorNode;
import senscript.Command;

public class Command_REGISTERPULSE extends Command {

    private String pulseTableVariable;
    private String neighbourIdVariable;

    public Command_REGISTERPULSE(SensorNode sensorNode, String pulseTableVariable, String neighbourIdVariable) {
        this.sensor = sensorNode;
        this.pulseTableVariable = pulseTableVariable;
        this.neighbourIdVariable = neighbourIdVariable;
    }

    @Override
    public double execute() {
        String neighbourId = sensor.getScript().getVariableValue(neighbourIdVariable);

        String serializedPulseTable = sensor.getScript().getVariableValue("$"+pulseTableVariable);
        PulseTable pulseTable = PulseTable.deserialize(serializedPulseTable);

        pulseTable.pulseNeighbour(neighbourId);
        return 0;
    }
}
