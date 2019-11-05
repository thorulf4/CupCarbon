package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETDATATest {

    @Test
    void execute() {
        TestableSensorNode sensor = new TestableSensorNode(1);

        DataPackage dataPackage = new DataPackage("5", "A", "hello world");
        sensor.putVariable("serializedPacket", dataPackage.serialize());

        Command_GETDATA getdata = new Command_GETDATA(sensor, "$serializedPacket", "output");

        sensor.addTime(60); // 1minute

        getdata.execute();

        assertEquals("hello world", sensor.getVariableValue("$output"));

    }
}