package d504;

public class PulseMessage {

    public String senderId;

    public String serialize() {
        return senderId;
    }

    public static PulseMessage deserialize(String data) {
        PulseMessage pulseMessage = new PulseMessage();
        pulseMessage.senderId = data;
        return pulseMessage;
    }
}
