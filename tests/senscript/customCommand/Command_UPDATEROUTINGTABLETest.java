package senscript.customCommand;

<<<<<<< Updated upstream
import d504.ConfigPackage;
import d504.TestableSensorNode;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Command_UPDATEROUTINGTABLETest {

    @Test
    void execute() {

        TestableSensorNode sensor = new TestableSensorNode(1);

        RoutingTable rt = new RoutingTable();
        rt.addEntry("A", "1", 10);
        rt.addEntry("A", "2", 5);
        rt.addEntry("B", "2", 1);
        String serializedConfigPackage = "20&A&5&C&10";
        ConfigPackage config = ConfigPackage.deserialize(serializedConfigPackage);

        sensor.putVariable("rt", rt.serialize());
        sensor.putVariable("config", config.serialize());

        Command_UPDATEROUTINGTABLE updateRt =
                new Command_UPDATEROUTINGTABLE(sensor, "rt", "$config", "output");

        updateRt.execute();
        assertEquals("true", sensor.getVariableValue("$output"));
    }

=======
public class Command_UPDATEROUTINGTABLETest {
>>>>>>> Stashed changes
}
