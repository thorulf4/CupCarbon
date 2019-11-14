package senscript.customCommand;

import d504.TestableSensorNode;
import d504.utils.Serialize;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Command_ITERATETest {

    @Test
    void execute_WithMultipleElements() {
        TestableSensorNode sensor = new TestableSensorNode(1);

        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("to");
        list.add("you");

        sensor.putVariable("list", Serialize.serialize(list));

        Command command = new Command_ITERATE(sensor, "list", "nextElement");
        command.execute();

        String element = sensor.getVariableValue("$nextElement");

        assertEquals("Hello", element);
    }

    @Test
    void execute_WithOneElement() {
        TestableSensorNode sensor = new TestableSensorNode(1);

        List<String> list = new ArrayList<>();
        list.add("Hello");

        sensor.putVariable("list", Serialize.serialize(list));

        Command command = new Command_ITERATE(sensor, "list", "nextElement");
        command.execute();

        String element = sensor.getVariableValue("$nextElement");

        assertEquals("Hello", element);
    }

    @Test
    void execute_WithNoElements() {
        TestableSensorNode sensor = new TestableSensorNode(1);

        List<String> list = new ArrayList<>();

        sensor.putVariable("list", Serialize.serialize(list));

        Command command = new Command_ITERATE(sensor, "list", "nextElement");

        assertThrows(RuntimeException.class, () -> command.execute());
    }

    @Test
    void execute_WithMultipleReads() {
        TestableSensorNode sensor = new TestableSensorNode(1);

        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("to");
        list.add("you");

        sensor.putVariable("list", Serialize.serialize(list));

        Command command = new Command_ITERATE(sensor, "list", "nextElement");
        command.execute();

        String element = sensor.getVariableValue("$nextElement");

        assertEquals("Hello", element);

        command.execute();

        element = sensor.getVariableValue("$nextElement");

        assertEquals("to", element);

        command.execute();

        element = sensor.getVariableValue("$nextElement");

        assertEquals("you", element);
    }
}