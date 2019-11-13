package d504.backupRouting;

import d504.DataPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTableTest {

    @Test
    void serialize() {
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage("m13", 3600,"3", new DataPackage("m13","1", "hi"));
        messageTable.addReceiver("m13", "5");
        messageTable.addMessage("a14", 3600,"2", new DataPackage("a14","1", "no"));
        messageTable.addReceiver("a14", "7");

        assertEquals("a14&8&2&3600&2&0.0&a14&1&no&7&m13&8&3&3600&2&0.0&m13&1&hi&5", messageTable.serialize());
    }

    @Test
    void deserialize() {
        MessageTable messageTable = MessageTable.deserialize("a14&8&2&3600&2&0.0&a14&1&no&7&m13&8&3&3600&2&0.0&m13&1&hi&5");

        assertEquals("a14&8&2&3600&2&0.0&a14&1&no&7&m13&8&3&3600&2&0.0&m13&1&hi&5",messageTable.serialize());
        assertEquals(1, messageTable.getReceivers("m13").size());
        assertEquals(1, messageTable.getReceivers("a14").size());

    }
}