package senscript.customCommand;

import d504.TestableSensorNode;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETNEXTRECEIVERTest {


    @Test
    void onlyOneReceiver() {

        TestableSensorNode testNode = new TestableSensorNode(1);

        testNode.putVariable("nextReceivers", "Receiver1&");

        Command_GETNEXTRECEIVER getNextReceiver = new Command_GETNEXTRECEIVER(testNode, "nextReceivers", "receiver", "moreLeft");

        getNextReceiver.execute();

       assertEquals( "false", testNode.getVariableValue("$moreLeft"));
    }


    @Test
    void moreReceivers(){
        TestableSensorNode testNode = new TestableSensorNode(1);

        testNode.putVariable("nextReceivers", "Receiver1&Receiver2&Receiver3");

        Command_GETNEXTRECEIVER getNextReceiver = new Command_GETNEXTRECEIVER(testNode, "nextReceivers", "receiver", "moreLeft");

        getNextReceiver.execute();

        assertEquals( "true", testNode.getVariableValue("$moreLeft"));
    }

    @Test
    void theNextReceiverHasBeenRemovedFromTheListOfReceivers(){

        TestableSensorNode testNode = new TestableSensorNode(1);

        testNode.putVariable("nextReceivers", "Receiver1&Receiver2&Receiver3");

        Command_GETNEXTRECEIVER getNextReceiver = new Command_GETNEXTRECEIVER(testNode, "nextReceivers", "receiver", "moreLeft");

        getNextReceiver.execute();

        assertEquals("Receiver2&Receiver3", testNode.getVariableValue("$nextReceivers"));
    }

    @Test
    void theNextReceiver(){

        TestableSensorNode testNode = new TestableSensorNode(1);

        testNode.putVariable("nextReceivers", "Receiver1&Receiver2&Receiver3");

        Command_GETNEXTRECEIVER getNextReceiver = new Command_GETNEXTRECEIVER(testNode, "nextReceivers", "receiver", "moreLeft");

        getNextReceiver.execute();

        assertEquals("Receiver1", testNode.getVariableValue("$receiver"));
    }




}