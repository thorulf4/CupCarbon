package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import d504.utils.Serialize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Command_TICKCONGATIMERTest {

    private Command_TICKCONGATIMER command;
    private TestableSensorNode sensorNode;
    private String messageTableVariable;
    private String previousTimeVariable;
    private String timedoutMessagesVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensorNode = new TestableSensorNode(1);
        messageTableVariable = "messageTableVariable";
        previousTimeVariable = "previousTimeVariable";
        timedoutMessagesVariable = "timedoutMessagesVariable";
    }

    void createCommand(MessageTable messageTable, double timer){
        sensorNode.putVariable(messageTableVariable, messageTable.serialize());
        sensorNode.putVariable(previousTimeVariable, String.valueOf(timer));
        command = new Command_TICKCONGATIMER(sensorNode, messageTableVariable, previousTimeVariable, timedoutMessagesVariable);
    }

    @Test
    void returned_previous_time_is_correct(){
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(1000, "2", new DataPackage("A", "data"));
        messageTable.addMessage(1000,"2", new DataPackage("B", "data"));

        createCommand(messageTable, 0d);
        sensorNode.addTime(1);
        command.execute();
        sensorNode.addTime(1);
        command.execute();
        double newTimer = Double.parseDouble(sensorNode.getVariableValue("$" + previousTimeVariable));

        assertEquals(2, newTimer);
    }

    @Test
    void returns_correct_amount_of_timed_out_messages(){
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(1000,"2", new DataPackage("A", "data"));
        messageTable.addMessage(1000,"2", new DataPackage("B", "data"));

        createCommand(messageTable, 0d);
        sensorNode.addTime(1);
        command.execute();
        sensorNode.addTime(1);
        command.execute();
        sensorNode.addTime(1);
        command.execute();
        List<String> timedoutMessages = Serialize.deserializeStringList(sensorNode.getVariableValue("$" + timedoutMessagesVariable));

        assertEquals(2, timedoutMessages.size());
    }

    @Test
    void returns_correct_amount_of_timed_out_messages2(){
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(1000, "2", new DataPackage("A", "data"));

        createCommand(messageTable, 0d);
        sensorNode.addTime(1);
        command.execute();
        messageTable.addMessage(1000,"2", new DataPackage("B", "data"));
        sensorNode.addTime(1);
        command.execute();
        sensorNode.addTime(1);
        command.execute();
        List<String> timedoutMessages = Serialize.deserializeStringList(sensorNode.getVariableValue("$" + timedoutMessagesVariable));

        assertEquals(1, timedoutMessages.size());
    }
}