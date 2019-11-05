package senscript.customCommand;

import d504.PackageType;
import d504.TestableSensorNode;
import d504.TypedPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_DECIPHERTest {

    @Test
    void executeTest() {
        TestableSensorNode sensor = new TestableSensorNode(0);

        TypedPackage typedPackage = new TypedPackage(PackageType.Pulse, "5", "someData&otherData");
        sensor.putVariable("inputPacket", typedPackage.serialize());

        Command_DECIPHER commandDecipher = new Command_DECIPHER(sensor, "$inputPacket", "dataOutput", "typeOutput", "senderOutput");
        commandDecipher.execute();

        assertEquals("5", sensor.getVariableValue("$senderOutput"), "Expected senderOuput to be 5");
        assertEquals("someData&otherData", sensor.getVariableValue("$dataOutput"), "Expected dataOutput to be someData&otherData");

        PackageType type = PackageType.values()[Integer.parseInt(sensor.getVariableValue("$typeOutput"))];
        assertEquals(PackageType.Pulse, type, "Expected typeOutput to be Pulse");
    }
    
}