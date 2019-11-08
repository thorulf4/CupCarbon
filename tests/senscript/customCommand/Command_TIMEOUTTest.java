package senscript.customCommand;

import d504.TestableSensorNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_TIMEOUTTest {


    @Test
    void fixedTimeTestTimeout(){

        TestableSensorNode testNode = new TestableSensorNode(2);
        // Time on simulation now
        testNode.addTime(3002);

        Command_TIMEOUT command_timeout = new Command_TIMEOUT(testNode, "1", "output");

        command_timeout.execute();

        assertEquals("true", testNode.getVariableValue("$output"));
    }

    @Test
    void fixedTimeTestJustTimedOut(){
        TestableSensorNode testNode = new TestableSensorNode(2);

        testNode.addTime(3001);

        Command_TIMEOUT command_timeout = new Command_TIMEOUT(testNode, "1", "output");

        command_timeout.execute();

        assertEquals("true", testNode.getVariableValue("$output"));
    }

    @Test
    void fixedTimeTestNoTimeout(){
        TestableSensorNode testNode = new TestableSensorNode(2);

        testNode.addTime(1001);

        Command_TIMEOUT command_timeout = new Command_TIMEOUT(testNode, "1", "output");

        command_timeout.execute();

        assertEquals("false", testNode.getVariableValue("$output"));
    }

    @Test
    void selectedTimeTestTimeout(){

        TestableSensorNode testNode = new TestableSensorNode(2);
        testNode.addTime(2500);

        // Set the limit to 1000
        Command_TIMEOUT  command_timeout = new Command_TIMEOUT(testNode, "25", "1000", "output");

        command_timeout.execute();

        assertEquals("true", testNode.getVariableValue("$output"));
    }

    @Test
    void selectedTimeTestJustTimedOut(){
        TestableSensorNode testNode = new TestableSensorNode(2);
        testNode.addTime(3001);

        Command_TIMEOUT command_timeout = new Command_TIMEOUT(testNode, "1", "3000", "output");
        command_timeout.execute();

        assertEquals("true", testNode.getVariableValue("$output"));
    }

    @Test
    void selectedTimeTestNoTimeout(){
        TestableSensorNode testNode = new TestableSensorNode(2);

        testNode.addTime(2000);

        Command_TIMEOUT  command_timeout = new Command_TIMEOUT(testNode, "25", "4000", "output");

        command_timeout.execute();

        assertEquals("false", testNode.getVariableValue("$output"));
    }

}