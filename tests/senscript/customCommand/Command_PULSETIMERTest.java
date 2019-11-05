package senscript.customCommand;


import d504.PulseMessage;
import d504.TestableSensorNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Command_PULSETIMERTest {

    @Test
    void execute(){

        TestableSensorNode sensor = new TestableSensorNode(1);
        sensor.addTime(20);
        String timeVariable= "10";
        String interval = "0";

        Command_PULSETIMER pulsetimer =
                new Command_PULSETIMER(sensor, timeVariable, interval, "output");

        pulsetimer.execute();

        assertEquals("true", sensor.getVariableValue("$output"));

    }
}
