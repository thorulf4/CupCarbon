package senscript.customCommand;

import d504.ISensorNode;
import d504.utils.Serialize;

import java.util.ArrayList;
import java.util.List;

public class Command_ITERATE extends Command{

    private String serializedListVariable;
    private String outputElement;

    public Command_ITERATE(ISensorNode sensorNode, String serializedListVariable, String outputElement) {
        this.sensor = sensorNode;
        this.serializedListVariable = serializedListVariable;
        this.outputElement = outputElement;
    }

    @Override
    public double execute() {
        String serializedList = sensor.getVariableValue("$"+serializedListVariable);

        if(serializedList.equals(""))
            throw new RuntimeException("Cannot iterate over an empty list");

        String element = Serialize.nextElement(serializedList);
        serializedList = Serialize.removeElements(serializedList, 1);

        sensor.putVariable(serializedListVariable, serializedList);
        sensor.putVariable(outputElement, element);
        return 0;
    }
}
