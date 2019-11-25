package d504.backupRouting;

import d504.DataPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTableTest {

    @Test
    void serialize() {
        MessageTable messageTable = new MessageTable();

        messageTable.addMessage(3600,"3", new DataPackage("m13","1", "hi"));
        messageTable.addReceiver("m13", "5");
        messageTable.addMessage(3600,"2", new DataPackage("a14","1", "no"));
        messageTable.addReceiver("a14", "7");

        assertEquals("a14&8&2&3600&2&2.0&a14&1&no&7&m13&8&3&3600&2&2.0&m13&1&hi&5", messageTable.serialize());
    }

    @Test
    void deserialize() {
        MessageTable messageTable = MessageTable.deserialize("a14&8&2&3600&2&0.0&a14&1&no&7&m13&8&3&3600&2&0.0&m13&1&hi&5");

        assertEquals("a14&8&2&3600&2&0.0&a14&1&no&7&m13&8&3&3600&2&0.0&m13&1&hi&5",messageTable.serialize());
        assertEquals(1, messageTable.getReceivers("m13").size());
        assertEquals(1, messageTable.getReceivers("a14").size());

    }

    @Test
    void tickTimers_test(){
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(1000,"2", new DataPackage("A", "data"));
        messageTable.addMessage(1000,"3", new DataPackage("B", "data"));

        messageTable.tickTimers(2d);

        assertEquals(2, messageTable.getTimedOutMessages().size());
    }

    @Test
    void tickTimers_test2(){
        MessageTable messageTable = new MessageTable();
        messageTable.addMessage(1000,"2", new DataPackage("A", "data"));
        messageTable.addMessage(1000,"3", new DataPackage("B", "data"));

        messageTable.tickTimers(1d);

        assertEquals(0, messageTable.getTimedOutMessages().size());
    }

    @Test
    void messagesWithDisabledTimerIsNotReturnedWhenGettingTimedOutMessages(){
        MessageTable messageTable = new MessageTable();
        DataPackage dataPackage = new DataPackage("A", "data");
        messageTable.addMessage(1000, "2", dataPackage);

        messageTable.tickTimers(2d);
        messageTable.decreaseCongaStep(dataPackage.getMessageID());
        messageTable.tickTimers(1d);
        messageTable.decreaseCongaStep(dataPackage.getMessageID());

        assertEquals(0, messageTable.getTimedOutMessages().size());
    }

    @Test
    void isMessagePresent_messageIsInMessageTable_shouldReturnTrue(){
        MessageTable messageTable = new MessageTable();
        DataPackage dataPackage = new DataPackage("A", "data");

        messageTable.addMessage(1000, "2", dataPackage);

        assertTrue(messageTable.isMessagePresent(dataPackage.getMessageID()));
    }

    @Test
    void isMessagePresent_messageIsNotInMessageTable_shouldReturnFalse(){
        MessageTable messageTable = new MessageTable();
        DataPackage dataPackage1 = new DataPackage("A", "data1");
        DataPackage dataPackage2 = new DataPackage("A", "data2");

        messageTable.addMessage(1000, "2", dataPackage1);

        assertFalse(messageTable.isMessagePresent(dataPackage2.getMessageID()));
    }

    @Test
    void isMessagePresent_messageTableIsEmpty_shouldReturnFalse(){
        MessageTable messageTable = new MessageTable();
        DataPackage dataPackage = new DataPackage("A", "data");

        assertFalse(messageTable.isMessagePresent(dataPackage.getMessageID()));
    }

    @Test
    void decreaseCongaStepForRelayAck_congaShouldNotBeActive(){
        DataPackage dataPackage = new DataPackage("A", "data");
        MessageTable messageTable = new MessageTable();

        messageTable.addMessage(1000, "2", dataPackage);
        messageTable.decreaseCongaStepForRelayAck(dataPackage.getMessageID());

        assertFalse(messageTable.isCongaActive(dataPackage.getMessageID()));
    }

    @Test
    void decreaseCongaStepForRelayAck_timerShouldBeDisabled(){
        DataPackage dataPackage = new DataPackage("A", "data");
        MessageTable messageTable = new MessageTable();

        messageTable.addMessage(1000, "2", dataPackage);
        messageTable.decreaseCongaStepForRelayAck(dataPackage.getMessageID());
        messageTable.tickTimers(999999);

        assertTrue(messageTable.getTimedOutMessages().isEmpty());
    }
}