package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_ADDTOMTTest {

    @Test
    void AddToMTTest1(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        String senderId = "3";
        sensor.putVariable("senderId", senderId);

        DataPackage dataPackage = new DataPackage("m1", "R3", "hello world");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        MessageTable table = new MessageTable();
        sensor.putVariable("MT", table.serialize());

        Command_ADDTOMT command = new Command_ADDTOMT(sensor, "MT", "$dataPackage", "$senderId");
        command.execute();

        MessageTable editedTable = MessageTable.deserialize(sensor.getVariableValue("$MT"));

        assertTrue(editedTable.isMessagePresent("m1"), "Expected m1 to be registered in MT");
    }

    @Test
    void AddToMTTest2(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        String senderId = "3";
        sensor.putVariable("senderId", senderId);

        DataPackage dataPackage = new DataPackage("m1", "R3", "hello world");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        DataPackage dataPackage2 = new DataPackage("m2", "R3", "hello world");
        sensor.putVariable("dataPackage2", dataPackage2.serialize());

        MessageTable table = new MessageTable();
        sensor.putVariable("MT", table.serialize());

        Command_ADDTOMT command = new Command_ADDTOMT(sensor, "MT", "$dataPackage", "$senderId");
        command.execute();

        command = new Command_ADDTOMT(sensor, "MT", "$dataPackage2", "$senderId");
        command.execute();

        MessageTable editedTable = MessageTable.deserialize(sensor.getVariableValue("$MT"));

        assertTrue(editedTable.isMessagePresent("m1"), "Expected m1 to be registered in MT");
        assertTrue(editedTable.isMessagePresent("m2"), "Expected m2 to be registered in MT");
    }

}