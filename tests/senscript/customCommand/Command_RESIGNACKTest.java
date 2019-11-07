package senscript.customCommand;

import d504.TestableSensorNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Command_RESIGNACKTest {

    @Test
    void execute(){

        TestableSensorNode sensor = new TestableSensorNode(1);

        String input = "test";
        sensor.putVariable("input", input);

        Command_RESIGNACK ra = new Command_RESIGNACK(sensor, "$input", "output");

        ra.execute();

        assertEquals("3&1&test", sensor.getVariableValue("$output"));
    }
}
