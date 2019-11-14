package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import d504.utils.Serialize;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETNEXTRECEIVERTest {


    @Test
    void onlyOneReceiver() {

        TestableSensorNode testNode = new TestableSensorNode(1);

        MessageTable mt = new MessageTable();

        DataPackage dataPackage = new DataPackage("2", "A", "AlphaKilo");

        mt.addMessage(3600, "S3", dataPackage);

        mt.addReceiver("2", "S5");

        List<String> receivers = mt.getReceivers("2");

        testNode.putVariable("nextReceivers", Serialize.serialize(receivers));

        Command_GETNEXTRECEIVER getNextReceiver = new Command_GETNEXTRECEIVER(testNode, "nextReceivers", "receiver", "moreLeft");

        getNextReceiver.execute();

       assertEquals( "false", testNode.getVariableValue("$moreLeft"));
    }


    @Test
    void moreReceivers(){
        TestableSensorNode testNode = new TestableSensorNode(1);

        MessageTable mt = new MessageTable();

        DataPackage dataPackage = new DataPackage("33", "A", "AlphaKilo");

        mt.addMessage(3600, "S3", dataPackage);

        mt.addReceiver("33", "S5");

        mt.addReceiver("33", "S7");

        List<String> receivers = mt.getReceivers("33");

        testNode.putVariable("nextReceivers", Serialize.serialize(receivers));

        Command_GETNEXTRECEIVER getNextReceiver = new Command_GETNEXTRECEIVER(testNode, "nextReceivers", "receiver", "moreLeft");

        getNextReceiver.execute();

        assertEquals( "true", testNode.getVariableValue("$moreLeft"));
    }

    @Test
    void theNextReceiverHasBeenRemovedFromTheListOfReceivers(){

        TestableSensorNode testNode = new TestableSensorNode(1);

        MessageTable mt = new MessageTable();

        DataPackage dataPackage = new DataPackage("33", "A", "AlphaKilo");

        mt.addMessage(3600,"S3", dataPackage);

        mt.addReceiver("33", "S5");

        mt.addReceiver("33", "S7");

        mt.addReceiver("33", "S9");

        List<String> receivers = mt.getReceivers("33");

        testNode.putVariable("nextReceivers", Serialize.serialize(receivers));

        Command_GETNEXTRECEIVER getNextReceiver = new Command_GETNEXTRECEIVER(testNode, "nextReceivers", "receiver", "moreLeft");

        getNextReceiver.execute();

        assertEquals("S7&S9", testNode.getVariableValue("$nextReceivers"));
    }

    @Test
    void theNextReceiver(){

        TestableSensorNode testNode = new TestableSensorNode(1);

        MessageTable mt = new MessageTable();

        DataPackage dataPackage = new DataPackage("33", "A", "AlphaKilo");

        mt.addMessage(3600,"S3", dataPackage);

        mt.addReceiver("33", "S5");

        mt.addReceiver("33", "S7");

        List<String> receivers = mt.getReceivers("33");

        testNode.putVariable("nextReceivers", Serialize.serialize(receivers));

        Command_GETNEXTRECEIVER getNextReceiver = new Command_GETNEXTRECEIVER(testNode, "nextReceivers", "receiver", "moreLeft");

        getNextReceiver.execute();

        assertEquals("S5", testNode.getVariableValue("$receiver"));
    }




}