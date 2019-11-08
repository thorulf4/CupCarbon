package senscript.customCommand;


import d504.PulseMessage;
import d504.TestableSensorNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Command_PULSETIMERTest {

    @Test
    void execute01(){

        TestableSensorNode sensor = new TestableSensorNode(1);
        sensor.addTime(20);
        String timeVariable= "10";
        String interval = "0";

        sensor.putVariable("timeVariable", timeVariable);
        sensor.putVariable("interval", interval);

        Command_PULSETIMER pulseTimer =
                new Command_PULSETIMER(sensor, "$timeVariable", "$interval", "output");

        pulseTimer.execute();

        assertEquals("true", sensor.getVariableValue("$output"));

    }

    @Test
    void execute02(){

        TestableSensorNode sensor = new TestableSensorNode(1);
        sensor.addTime(500);
        String timeVariable = "75";
        String interval = "1";

        sensor.putVariable("timeVariable", timeVariable);
        sensor.putVariable("interval", interval);

        Command_PULSETIMER pulseTimer =
                new Command_PULSETIMER(sensor, "$timeVariable", "$interval", "output");

        pulseTimer.execute();

        assertEquals("false", sensor.getVariableValue("$output"));

    }
}
