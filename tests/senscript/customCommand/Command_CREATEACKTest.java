package senscript.customCommand;

import d504.AckMessage;
import d504.DataPackage;
import d504.ISensorNode;
import d504.TestableSensorNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_CREATEACKTest {

    private Command_CREATEACK command;
    private ISensorNode sensorNode;
    private String outputAck;
    private String dataVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        outputAck = "outputAck";
        dataVariable = "dataVariable";
        sensorNode = new TestableSensorNode(1);
    }

    void createCommand(DataPackage dataPackage){
        sensorNode.putVariable(dataVariable, dataPackage.serialize());
        command = new Command_CREATEACK(sensorNode, dataVariable, outputAck);
    }

    @Test
    void Ack_not_empty(){
        DataPackage dataPackage = new DataPackage("A", "data");

        createCommand(dataPackage);
        command.execute();
        AckMessage ack = AckMessage.deserialize(sensorNode.getVariableValue(outputAck));

        assertTrue(ack.serialize().length() > 0);
    }
}