package senscript.customCommand;

import d504.ISensorNode;
import d504.TestableSensorNode;
import d504.utils.Serialize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Command_GETNEXTRELAYTest {

    private ISensorNode sensorNode;
    private Command_GETNEXTRELAY command;
    private String relayListVariable;
    private String relayIdVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensorNode = new TestableSensorNode(1);

        relayListVariable = "relayListVariable";
        relayIdVariable = "relayIdVariable";
    }

    void createCommand(String relayList){
        sensorNode.putVariable(relayListVariable, relayList);

        command = new Command_GETNEXTRELAY(sensorNode, relayListVariable, relayIdVariable);
    }

    @Test
    void commandRemovesRelayFromList(){
        List<String> relayArrayList = new ArrayList<String>(){{
            add("A");
            add("B");
            add("C");
        }};
        String relayList = Serialize.serialize(relayArrayList);

        createCommand(relayList);
        command.execute();
        String newRelayList = sensorNode.getVariableValue("$" + relayListVariable);

        assertEquals(2, Serialize.deserializeStringList(newRelayList).size());
    }

    @Test
    void commandRemovesCorrectRelayFromList(){
        List<String> relayArrayList = new ArrayList<String>(){{
            add("A");
            add("B");
            add("C");
        }};
        String relayList = Serialize.serialize(relayArrayList);

        createCommand(relayList);
        command.execute();
        String newRelayList = sensorNode.getVariableValue("$" + relayListVariable);

        assertEquals("B", Serialize.nextElements(newRelayList, 2)[0]);
        assertEquals("C", Serialize.nextElements(newRelayList, 2)[1]);
    }

    @Test
    void commandReturnsCorrectRelayId(){
        List<String> relayArrayList = new ArrayList<String>(){{
            add("A");
            add("B");
            add("C");
        }};
        String relayList = Serialize.serialize(relayArrayList);

        createCommand(relayList);
        command.execute();
        String relayId = sensorNode.getVariableValue("$" + relayIdVariable);

        assertEquals("A", relayId);
    }
}