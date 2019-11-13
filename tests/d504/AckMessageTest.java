package d504;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AckMessageTest {

    @Test
    void exceptionWhenMessageIdIsEmpty(){
        assertThrows(RuntimeException.class, () -> new AckMessage(""));
    }

    @Test
    void exceptionWhenDeserializeMessageIsEmpty(){
        assertThrows(RuntimeException.class, () -> AckMessage.deserialize(""));
    }

}