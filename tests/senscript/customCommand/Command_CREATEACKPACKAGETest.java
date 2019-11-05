package senscript.customCommand;

import d504.AckMessage;
import d504.DataPackage;
import d504.TestableSensorNode;
import d504.TypedPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_CREATEACKPACKAGETest {

    @Test
    void createAckPackage(){
        TestableSensorNode sensor = new TestableSensorNode(1);

        DataPackage dataPackage = new DataPackage("m1", "R1", "Hello");
        sensor.putVariable("dataPackage", dataPackage.serialize());

        Command_CREATEACKPACKAGE command = new Command_CREATEACKPACKAGE(sensor, "$dataPackage", "outputAckPackage");
        command.execute();

        TypedPackage typedPackage = TypedPackage.deserialize(sensor.getVariableValue("$outputAckPackage"));
        AckMessage ackMessage = AckMessage.deserialize(typedPackage.packageData);

        assertEquals("m1", ackMessage.getMessageId());
    }

}