package senscript.customCommand;

import d504.ISensorNode;
import d504.TestableSensorNode;
import d504.routingTable.RelayRoutes;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Command_UPDATEROUTINGTABLELOWBATTERYTest {

    private ISensorNode sensorNode;
    private Command_UPDATEROUTINGTABLELOWBATTERY command;
    private final String routingTableVariable = "routingTableVariable";
    private final String shouldCreateConfigVariable = "shouldCreateConfigVariable";

    @BeforeEach
    void beforeEach(){
        command = null;
        sensorNode = new TestableSensorNode(1);
    }

    void createCommand(RoutingTable routingTable){
        sensorNode.putVariable(routingTableVariable, routingTable.serialize());

        command = new Command_UPDATEROUTINGTABLELOWBATTERY(sensorNode, routingTableVariable, shouldCreateConfigVariable);
    }

    @Test
    void singleRelayRoute_correctCosts(){
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("A", "1", 5);
        routingTable.addEntry("A", "2", 10);

        createCommand(routingTable);
        command.execute();
        RoutingTable newRoutingTable = RoutingTable.deserialize(sensorNode.getVariableValue("$" + routingTableVariable));
        List<RelayRoutes> relayRoutes = newRoutingTable.getRoutingTable();

        assertEquals(10, relayRoutes.get(0).getRoutes().first().getCost());
        assertEquals(15, relayRoutes.get(0).getRoutes().last().getCost());
    }

    @Test
    void multipleRelayRoute_correctCosts(){
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("A", "1", 5);
        routingTable.addEntry("A", "2", 10);
        routingTable.addEntry("B", "2", 10);
        routingTable.addEntry("B", "4", 20);

        createCommand(routingTable);
        command.execute();
        RoutingTable newRoutingTable = RoutingTable.deserialize(sensorNode.getVariableValue("$" + routingTableVariable));
        List<RelayRoutes> relayRoutes = newRoutingTable.getRoutingTable();

        assertEquals(10, relayRoutes.get(0).getRoutes().first().getCost());
        assertEquals(15, relayRoutes.get(0).getRoutes().last().getCost());
        assertEquals(20, relayRoutes.get(1).getRoutes().first().getCost());
        assertEquals(30, relayRoutes.get(1).getRoutes().last().getCost());
    }

    @Test
    void singleRelayRoute_singleNode(){
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("A", "1", 5);

        createCommand(routingTable);
        command.execute();
        RoutingTable newRoutingTable = RoutingTable.deserialize(sensorNode.getVariableValue("$" + routingTableVariable));
        List<RelayRoutes> relayRoutes = newRoutingTable.getRoutingTable();

        assertEquals(5, relayRoutes.get(0).getRoutes().first().getCost());
    }

}