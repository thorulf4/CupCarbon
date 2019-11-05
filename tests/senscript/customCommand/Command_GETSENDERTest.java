package senscript.customCommand;

import d504.DataPackage;
import d504.PulseMessage;
import d504.TestableSensorNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETSENDERTest {

    @Test
    void getSenderID(){
        TestableSensorNode testableSensorNode = new TestableSensorNode(1);

        PulseMessage pulseMessage = new PulseMessage("23");

        String s = pulseMessage.serialize();

        testableSensorNode.putVariable("pulseMessage", s);


        Command_GETSENDER getSender = new Command_GETSENDER(testableSensorNode, "$pulseMessage", "output");

        getSender.execute();

        assertEquals("23", testableSensorNode.getVariableValue("$output"));
    }

}