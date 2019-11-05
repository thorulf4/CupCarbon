package senscript.customCommand;

import d504.ISensorNode;
import d504.pulseTable.PulseTable;



public class Command_REGISTERPULSE extends Command {

    private String pulseTableVariable;
    private String neighbourIdVariable;

    public Command_REGISTERPULSE(ISensorNode sensorNode, String pulseTableVariable, String neighbourIdVariable) {
        this.sensor = sensorNode;
        this.pulseTableVariable = pulseTableVariable;
        this.neighbourIdVariable = neighbourIdVariable;
    }

    @Override
    public double execute() {
        String neighbourId = sensor.getVariableValue(neighbourIdVariable);

        String serializedPulseTable = sensor.getVariableValue("$"+pulseTableVariable);
        PulseTable pulseTable = PulseTable.deserialize(serializedPulseTable);

        pulseTable.pulseNeighbour(neighbourId);
        sensor.putVariable(pulseTableVariable, pulseTable.serialize());
        return 0;
    }
}
