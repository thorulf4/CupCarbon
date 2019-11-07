package senscript.customCommand;

import d504.PulseMessage;
import d504.TestableSensorNode;
import d504.TypedPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Command_CREATEPULSETest {

    @Test
    void execute(){

        TestableSensorNode sensor = new TestableSensorNode(1);


        Command_CREATEPULSE pulse = new Command_CREATEPULSE(sensor, "output");

        pulse.execute();

        assertEquals("2&1&1", sensor.getVariableValue("$output"));
    }
}
