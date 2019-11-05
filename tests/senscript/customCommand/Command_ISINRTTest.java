package senscript.customCommand;

import d504.PulseMessage;
import d504.TestableSensorNode;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Command_ISINRTTest {

    @Test
    void presentInRT(){
        TestableSensorNode sensor = new TestableSensorNode(2);
        RoutingTable rt = new RoutingTable();
        String rtVar = "rtVar";
        rt.addEntry("1","5",2);
        rt.addEntry("1","3", 1);
        rt.addEntry("8", "5", 4);
        rt.addEntry("8", "3",6);

        sensor.putVariable(rtVar,rt.serialize());
        PulseMessage message = new PulseMessage("3");
        String pulseVar = "x";
        String output = "y";
        sensor.putVariable(pulseVar,message.serialize());

        Command_ISINRT isinrt = new Command_ISINRT(sensor,rtVar, "$"+pulseVar,output);
        isinrt.execute();

        assertEquals("true", sensor.getVariableValue("$"+output));
    }

    @Test
    void notPresentInRT(){
        TestableSensorNode sensor = new TestableSensorNode(1);
        RoutingTable rt = new RoutingTable();
        String rtVar = "rtVar";
        rt.addEntry("1","5",2);
        rt.addEntry("1","3", 1);
        rt.addEntry("8", "5", 4);
        rt.addEntry("8", "3",6);
        sensor.putVariable(rtVar,rt.serialize());

        PulseMessage message = new PulseMessage("2");
        String pulseVar = "x";
        String output = "y";
        sensor.putVariable(pulseVar,message.serialize());

        Command_ISINRT isinrt = new Command_ISINRT(sensor,rtVar, "$"+pulseVar,output);
        isinrt.execute();

        assertEquals("false", sensor.getVariableValue("$"+output));

    }

    @Test
    void emptyTest(){
        TestableSensorNode sensor = new TestableSensorNode(1);
        RoutingTable rt = new RoutingTable();
        String rtVar = "rtvar";
        sensor.putVariable(rtVar,rt.serialize());

        PulseMessage message = new PulseMessage("2");
        String pulseVar = "x";
        String output = "y";
        sensor.putVariable(pulseVar,message.serialize());

        Command_ISINRT isinrt = new Command_ISINRT(sensor,rtVar, "$"+pulseVar,output);
        isinrt.execute();

        assertEquals("false", sensor.getVariableValue("$"+output));

    }
}
