package senscript.customCommand;

import d504.ISensorNode;
import d504.utils.Serialize;
import device.SensorNode;

public class Command_GETNEXTRELAY extends Command {

    private String relayListVariable;
    private String relayIdVariable;

    public Command_GETNEXTRELAY(ISensorNode sensor, String relayListVariable, String relayIdVariable) {
        this.sensor = sensor;
        this.relayListVariable = relayListVariable;
        this.relayIdVariable = relayIdVariable;
    }

    @Override
    public double execute() {
        String relayList = sensor.getVariableValue("$" + relayListVariable);

        if(relayList.isEmpty()){
            throw new RuntimeException("relayList is empty");
        }

        String relayId = Serialize.nextElement(relayList);
        relayList = Serialize.removeElements(relayList, 1);

        sensor.putVariable(relayIdVariable, relayId);
        sensor.putVariable(relayListVariable, relayList);

        return 0;
    }
}
