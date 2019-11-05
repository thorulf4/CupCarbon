package senscript.customCommand;

import d504.ConfiguredNodesTable;
import d504.ISensorNode;
import d504.PulseMessage;



public class Command_CHECKCONFIGUREDNODES extends Command {

    private String configuredNodesTableVariable;
    private String pulseMessageVariable;
    private String isInTableVariable;

    public Command_CHECKCONFIGUREDNODES(ISensorNode sensor, String configuredNodesTableVariable, String pulseMessageVariable, String isInTableVariable) {
        this.sensor = sensor;
        this.configuredNodesTableVariable = configuredNodesTableVariable;
        this.pulseMessageVariable = pulseMessageVariable;
        this.isInTableVariable = isInTableVariable;
    }

    @Override
    public double execute() {
        ConfiguredNodesTable configuredNodesTable = getConfiguredNodesTable();
        PulseMessage pulseMessage = getPulseMessage();

        boolean isInTable = configuredNodesTable.isNodeInTable(pulseMessage.senderId);
        if(!isInTable){
            configuredNodesTable.add(pulseMessage.senderId);
        }

        sensor.putVariable(configuredNodesTableVariable, configuredNodesTable.serialize());
        sensor.putVariable(isInTableVariable, Boolean.toString(isInTable));

        return 0;
    }

    private PulseMessage getPulseMessage() {
        String serializedPulse = sensor.getVariableValue(pulseMessageVariable);
        return PulseMessage.deserialize(serializedPulse);
    }

    private ConfiguredNodesTable getConfiguredNodesTable() {
        String serializedTable = sensor.getVariableValue("$" + configuredNodesTableVariable);
        return ConfiguredNodesTable.deserialize(serializedTable);
    }
}
