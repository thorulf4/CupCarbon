package senscript.customCommand;

import d504.ISensorNode;
import d504.backupRouting.MessageTable;

public class Command_TICKEXPIRATIONTIMERS extends Command {

    private String serializedMessageTableVariable;

    public Command_TICKEXPIRATIONTIMERS(ISensorNode sensorNode, String serializedMessageTableVariable) {
        this.sensor = sensorNode;
        this.serializedMessageTableVariable = serializedMessageTableVariable;
    }

    @Override
    public double execute() {
        MessageTable messageTable = MessageTable.deserialize(sensor.getVariableValue("$"+serializedMessageTableVariable));

        messageTable.tickExpirationTimers((long) sensor.getSimulationTime());

        sensor.putVariable(serializedMessageTableVariable, messageTable.serialize());
        return 0;
    }
}
