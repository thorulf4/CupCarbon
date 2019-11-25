package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Command_ISINMTTest {

    @Test
    void IsInMT1(){
        TestableSensorNode sensor = new TestableSensorNode(2);
        String output = "x";
        String mtVar = "mt";
        String messageVar = "messageVar";

        Command_ISINMT isinmt = new Command_ISINMT(sensor, mtVar, "$" + messageVar, output);

        DataPackage message = new DataPackage("12","1","Hello world");
        DataPackage message2 = new DataPackage("10","1","Bye world");

        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(3600,"1",message);
        messageTable.addMessage(3600,"1",message2);

        sensor.putVariable(mtVar, messageTable.serialize());
        sensor.putVariable(messageVar, message.serialize());

        isinmt.execute();
        assertEquals("true", sensor.getVariableValue("$"+output));

    }

    @Test
    void IsInMT2(){
        TestableSensorNode sensor = new TestableSensorNode(3);
        String output = "x";
        String mtVar = "mt";
        String messageVar = "message";
        Command_ISINMT isinmt = new Command_ISINMT(sensor, mtVar, "$" + messageVar, output);

        DataPackage message = new DataPackage("12","1","Hello world");
        DataPackage message2 = new DataPackage("10","3","Bye world");
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(3600,"1",message);
        messageTable.addMessage(3600,"1",message2);

        sensor.putVariable(mtVar,messageTable.serialize());
        sensor.putVariable(messageVar, message2.serialize());

        isinmt.execute();
        assertEquals("true",sensor.getVariableValue("$"+output));
    }

    @Test
    void CheckInEmpty(){
        TestableSensorNode sensor = new TestableSensorNode(2);
        String mtvar = "mt";
        String output = "x";
        String messgeVar = "messageVar";
        Command_ISINMT isinmt = new Command_ISINMT(sensor, mtvar, "$" + messgeVar, output);

        MessageTable messageTable = new MessageTable();
        DataPackage message = new DataPackage("12","1","Hello world");

        sensor.putVariable(mtvar,messageTable.serialize());
        sensor.putVariable(messgeVar, message.serialize());

        isinmt.execute();

        assertEquals("false",sensor.getVariableValue("$"+output));
    }

}
