package senscript.customCommand;

import d504.DataPackage;
import d504.PackageType;
import d504.TestableSensorNode;
import d504.TypedPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class Command_CREATEDATAPACKAGETest {

    @Test
    void makeDataPackage(){
        TestableSensorNode sensor = new TestableSensorNode(5);
        String relayIDvar = "relayVar";
        String output = "output";
        sensor.putVariable(relayIDvar,"D2");

        Command_CREATEDATAPACKAGE createdatapackage = new Command_CREATEDATAPACKAGE(sensor, "$"+relayIDvar, output);
        createdatapackage.execute();

        TypedPackage typedPackage = TypedPackage.deserialize(sensor.getVariableValue("$"+output));

        assertEquals("5", typedPackage.nodeID);
        assertEquals(PackageType.Data, typedPackage.type);

        DataPackage dataPackage = DataPackage.deserialize(typedPackage.packageData);

        assertEquals("FIRE", dataPackage.getData());
        assertEquals("D2", dataPackage.getTargetRelay());

    }

}
