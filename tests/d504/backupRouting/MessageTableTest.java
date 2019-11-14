package d504.backupRouting;

import d504.DataPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTableTest {

    @Test
    void serialize() {
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage("3", new DataPackage("m13","1", "hi"));
        messageTable.addReceiver("m13", "5");
        messageTable.addMessage("2", new DataPackage("a14","1", "no"));
        messageTable.addReceiver("a14", "7");

        assertEquals("a14&7&2&2&2.0&a14&1&no&7&m13&7&3&2&2.0&m13&1&hi&5", messageTable.serialize());
    }

    @Test
    void deserialize() {
        MessageTable messageTable = MessageTable.deserialize("a14&7&2&2&0.0&a14&1&no&7&m13&7&3&2&0.0&m13&1&hi&5");

        assertEquals("a14&7&2&2&0.0&a14&1&no&7&m13&7&3&2&0.0&m13&1&hi&5",messageTable.serialize());
        assertEquals(1, messageTable.getReceivers("m13").size());
        assertEquals(1, messageTable.getReceivers("a14").size());

    }

    @Test
    void tickTimers_test(){
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage("2", new DataPackage("A", "data"));
        messageTable.addMessage("3", new DataPackage("B", "data"));

        messageTable.tickTimers(2d);

        assertEquals(2, messageTable.getTimedOutMessages().size());
    }

    @Test
    void tickTimers_test2(){
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage("2", new DataPackage("A", "data"));
        messageTable.addMessage("3", new DataPackage("B", "data"));

        messageTable.tickTimers(1d);

        assertEquals(0, messageTable.getTimedOutMessages().size());
    }
}