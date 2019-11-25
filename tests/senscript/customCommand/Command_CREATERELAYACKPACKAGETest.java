package senscript.customCommand;

import d504.AckMessage;
import d504.DataPackage;
import d504.TestableSensorNode;
import d504.TypedPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_CREATERELAYACKPACKAGETest {

    private TestableSensorNode sensor;
    private Command_CREATERELAYACKPACKAGE command;
    private String dataPackageVariable;
    private String ackPackageVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensor = new TestableSensorNode(1);

        dataPackageVariable = "dataPackageVariable";
        ackPackageVariable = "ackPackageVariable";
    }

    void createCommand(DataPackage dataPackage){
        sensor.putVariable(dataPackageVariable, dataPackage.serialize());
        command = new Command_CREATERELAYACKPACKAGE(sensor, "$" + dataPackageVariable, ackPackageVariable);
    }

    @Test
    void returnsCorrectMessageIdInAck(){
        DataPackage dataPackage = new DataPackage("A", "data");
        createCommand(dataPackage);

        command.execute();
        TypedPackage typedPackage = TypedPackage.deserialize(sensor.getVariableValue("$" + ackPackageVariable));
        AckMessage ack = AckMessage.deserialize(typedPackage.packageData);

        assertEquals(dataPackage.getMessageID(), ack.getMessageId());
    }

}