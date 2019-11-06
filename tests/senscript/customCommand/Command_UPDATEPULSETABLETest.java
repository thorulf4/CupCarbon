package senscript.customCommand;

import d504.ISensorNode;
import d504.TestableSensorNode;
import d504.pulseTable.PulseTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_UPDATEPULSETABLETest {
    private ISensorNode sensorNode;
    private Command_UPDATEPULSETABLE command;
    private String pulseTableVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensorNode = new TestableSensorNode(1);

        pulseTableVariable = "pulseTableVariable";
    }

    void createCommand(PulseTable pulseTable){
        sensorNode.putVariable(pulseTableVariable, pulseTable.serialize());

        command = new Command_UPDATEPULSETABLE(sensorNode, pulseTableVariable);
    }

    @Test
    void shouldTickNode_singleNode(){
        PulseTable pulseTable = new PulseTable(1);
        pulseTable.pulseNeighbour("2");

        createCommand(pulseTable);
        command.execute();
        PulseTable newPulseTable = PulseTable.deserialize(sensorNode.getVariableValue("$"+pulseTableVariable));

        assertEquals(1, newPulseTable.getDeadNeighbours().size());
    }

    @Test
    void shouldTickNode_multipleNodes(){
        PulseTable pulseTable = new PulseTable(2);
        pulseTable.pulseNeighbour("2");
        pulseTable.pulseNeighbour("3");
        pulseTable.tickAllNeighbours();
        pulseTable.pulseNeighbour("4");

        createCommand(pulseTable);
        command.execute();
        PulseTable newPulseTable = PulseTable.deserialize(sensorNode.getVariableValue("$"+pulseTableVariable));

        assertEquals(2, newPulseTable.getDeadNeighbours().size());
    }
}