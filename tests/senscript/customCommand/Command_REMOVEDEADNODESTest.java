package senscript.customCommand;

import d504.ISensorNode;
import d504.TestableSensorNode;
import d504.pulseTable.PulseTable;
import d504.routingTable.RoutingTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Command_REMOVEDEADNODESTest {

    private ISensorNode sensorNode;
    private Command_REMOVEDEADNODES command;
    private String routingTableVariable;
    private String pulseTableVariable;
    private String hasQuickestRouteChangedVariable;

    @BeforeEach
    void beforeEach(){
        command = null;
        sensorNode = new TestableSensorNode(1);

        routingTableVariable = "routingTableVariable";
        pulseTableVariable = "pulseTableVariable";
        hasQuickestRouteChangedVariable = "boolVariable";
    }

    void createCommand(RoutingTable routingTable, PulseTable pulseTable){
        sensorNode.putVariable(routingTableVariable, routingTable.serialize());
        sensorNode.putVariable(pulseTableVariable, pulseTable.serialize());

        command = new Command_REMOVEDEADNODES(sensorNode, pulseTableVariable, routingTableVariable,
                hasQuickestRouteChangedVariable);
    }

    @Test
    void emptyRoutingAndPulseTableShouldNotChangeQuickestRoute(){
        createCommand(new RoutingTable(), new PulseTable(1));
        command.execute();

        boolean hasQuickestRoutesChanged = Boolean.parseBoolean(
                sensorNode.getVariableValue("$"+hasQuickestRouteChangedVariable));

        assertFalse(hasQuickestRoutesChanged);
    }

    @Test
    void removingOnlyNodeShouldChangeQuickestRoutes(){
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("A", "2", 1);
        PulseTable pulseTable = new PulseTable(1);
        pulseTable.pulseNeighbour("2");
        pulseTable.tickAllNeighbours();

        createCommand(routingTable, pulseTable);
        command.execute();
        boolean hasQuickestRoutesChanged = Boolean.parseBoolean(
                sensorNode.getVariableValue("$"+hasQuickestRouteChangedVariable));

        assertTrue(hasQuickestRoutesChanged);
    }

    @Test
    void QuickestRouteShouldChangeWhenRemovingQuickestNode(){
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("A", "2", 5);
        routingTable.addEntry("A", "5", 1);
        routingTable.addEntry("B", "2", 1);
        PulseTable pulseTable = new PulseTable(1);
        pulseTable.pulseNeighbour("5");
        pulseTable.tickAllNeighbours();
        pulseTable.pulseNeighbour("2");

        createCommand(routingTable, pulseTable);
        command.execute();
        boolean hasQuickestRoutesChanged = Boolean.parseBoolean(
                sensorNode.getVariableValue("$"+hasQuickestRouteChangedVariable));

        assertTrue(hasQuickestRoutesChanged);
    }

    @Test
    void returnedRoutingTableShouldContainFewerRelayRoutes(){
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("A", "2", 5);
        routingTable.addEntry("A", "5", 1);
        routingTable.addEntry("B", "2", 1);
        PulseTable pulseTable = new PulseTable(1);
        pulseTable.pulseNeighbour("2");
        pulseTable.tickAllNeighbours();
        pulseTable.pulseNeighbour("5");

        createCommand(routingTable, pulseTable);
        command.execute();
        RoutingTable newRoutingTable = RoutingTable.deserialize(
                sensorNode.getVariableValue("$"+routingTableVariable));

        assertTrue(newRoutingTable.getRoutingTable().size() == 1);
    }

    @Test
    void returnedRoutingTableShouldBeEmpty(){
        RoutingTable routingTable = new RoutingTable();
        routingTable.addEntry("A", "2", 1);
        PulseTable pulseTable = new PulseTable(1);
        pulseTable.pulseNeighbour("2");
        pulseTable.tickAllNeighbours();

        createCommand(routingTable, pulseTable);
        command.execute();
        RoutingTable newRoutingTable = RoutingTable.deserialize(
                sensorNode.getVariableValue("$"+routingTableVariable));

        assertTrue(newRoutingTable.getRoutingTable().isEmpty());
    }
}