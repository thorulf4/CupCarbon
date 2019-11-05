package senscript.customCommand;

import d504.DataPackage;
import d504.TestableSensorNode;
import d504.backupRouting.MessageTable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Command_ISINMTTest {

    @Test
    void execute(){
        TestableSensorNode sensor = new TestableSensorNode(2);
        DataPackage message = new DataPackage("12","1","Hello world");
        DataPackage message2 = new DataPackage("10","1","Bye world");
        String output = "x";
        String mtVar = "mt";
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(message.getMessageID(),"1",message);
        messageTable.addMessage(message2.getMessageID(),"1",message2);
        sensor.putVariable(mtVar,messageTable.serialize());

        Command_ISINMT isinmt = new Command_ISINMT(sensor,mtVar,message.serialize(),output);
        isinmt.execute();
        assertEquals("true",sensor.getVariableValue("$"+output));


    }

}
