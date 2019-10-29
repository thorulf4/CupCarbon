package d504;

public class PulseMessage {

    public String senderId;

    public PulseMessage(String senderId) {
        this.senderId = senderId;
    }

    public String serialize() {
        return senderId;
    }

    public static PulseMessage deserialize(String data) {
        if(data.contains("&")){
            throw new RuntimeException("Parameter data contained too many element separators");
        }

        return new PulseMessage(data);
    }
}
