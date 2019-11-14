package senscript.customCommand;

import d504.ISensorNode;
import d504.backupRouting.MessageTable;
import d504.utils.Serialize;

import java.util.List;

public class Command_TICKCONGATIMER extends Command{

    private String messageTableVariable;
    private String previousTimeVariable;
    private String timedoutMessageVariable;

    public Command_TICKCONGATIMER(ISensorNode sensorNode, String messageTableVariable, String previousTimeVariable, String timedoutMessageVariable) {
        this.sensor = sensorNode;
        this.messageTableVariable = messageTableVariable;
        this.previousTimeVariable = previousTimeVariable;
        this.timedoutMessageVariable = timedoutMessageVariable;
    }

    @Override
    public double execute() {
        double previousTime = getPreviousTime();
        double currentTime = getCurrentTime();

        if(previousTime != 0){
            MessageTable messageTable = getMessageTable();

            double deltaTime = currentTime - previousTime;
            messageTable.tickTimers(deltaTime);
            List<String> timedoutMessage = messageTable.getTimedOutMessages();

            String serializedTimedOutMessages = Serialize.serialize(timedoutMessage);
            sensor.putVariable(timedoutMessageVariable, serializedTimedOutMessages);
            sensor.putVariable(messageTableVariable, messageTable.serialize());
        }

        sensor.putVariable(previousTimeVariable, String.valueOf(currentTime));

        return 0;
    }

    private double getPreviousTime() {
        String timer = sensor.getVariableValue("$" + previousTimeVariable);
        if(timer == null || timer.isEmpty()){
            return 0d;
        }
        return Double.parseDouble(timer);
    }

    private double getCurrentTime() {
        return sensor.getSimulationTime();
    }

    private MessageTable getMessageTable() {
        String serializedTable = sensor.getVariableValue("$" + messageTableVariable);
        return MessageTable.deserialize(serializedTable);
    }
}
