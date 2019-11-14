package d504.backupRouting;

import d504.DataPackage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void serialize() {
        Message message = new Message("5", 1, new DataPackage("a17","1", "hello world"));
        message.receivers.add("3");
        message.receivers.add("5");

        assertEquals("5&1&0.0&a17&1&hello world&3&5", message.serialize());
    }

    @Test
    void deserialize() {
        Message message = Message.deserialize("5&0&0.0&a17&1&hello world&3&5");

        assertEquals("5", message.sender);
        assertEquals(0, message.congaStepsLeft);
        assertEquals("a17", message.dataPackage.getMessageID());
        assertEquals("1", message.dataPackage.getTargetRelay());
        assertEquals("hello world", message.dataPackage.getData());
        assertEquals("3", message.receivers.get(0));
        assertEquals("5", message.receivers.get(1));
    }

    @Test
    void tickTimer_returnsCorrectTimerAfterTick(){
        Message message = new Message("1", 2, new DataPackage("A", "data"));
        message.setTimerTimeLeft(1000d);

        message.tickTimer(500d);

        assertEquals(500d, message.getTimerTimeLeft());
    }

    @Test
    void tickTimer_cannotGoBelowZero(){
        Message message = new Message("1", 2, new DataPackage("A", "data"));
        message.setTimerTimeLeft(1000d);

        message.tickTimer(1200d);

        assertEquals(0d, message.getTimerTimeLeft());
    }

    @Test
    void tickTimer_disabledTimerDoesNotTickDown(){
        Message message = new Message("1", 2, new DataPackage("A", "data"));
        message.disableTimer();

        message.tickTimer(1200d);

        assertEquals(-1, message.getTimerTimeLeft());
    }
}