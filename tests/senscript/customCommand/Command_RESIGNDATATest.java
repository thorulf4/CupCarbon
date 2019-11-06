package senscript.customCommand;
import d504.DataPackage;
import d504.PackageType;
import d504.TestableSensorNode;
import d504.TypedPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Command_RESIGNDATATest {

    @Test
    void overideSenderID(){
        TestableSensorNode sensor = new TestableSensorNode(2);
        TestableSensorNode oldSender = new TestableSensorNode(1);
        String output = "w";
        String output1 = "ww";

        Command_CREATEDATAPACKAGE createdatapackage = new Command_CREATEDATAPACKAGE(oldSender, "ild mand", output1);
        createdatapackage.execute();

        TypedPackage typedpackage = TypedPackage.deserialize(oldSender.getVariableValue("$"+output1));

        Command_RESIGNDATA resigndata = new Command_RESIGNDATA(sensor,typedpackage.packageData,output);
        resigndata.execute();

        TypedPackage resignedPackage = TypedPackage.deserialize(sensor.getVariableValue("$"+output));

        DataPackage oldPackage = DataPackage.deserialize(typedpackage.packageData);
        DataPackage newPackage = DataPackage.deserialize(resignedPackage.packageData);


        assertEquals(oldPackage.getMessageID(),newPackage.getMessageID());
        assertEquals(oldPackage.getTargetRelay(), newPackage.getTargetRelay());
        assertEquals(oldPackage.getData(), newPackage.getData());

        assertEquals(PackageType.Data,resignedPackage.type);
        assertEquals("2",resignedPackage.nodeID);
    }
}
