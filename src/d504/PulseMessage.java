package d504;

import com.sun.javaws.exceptions.InvalidArgumentException;

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
            throw new RuntimeException("Parameter data contained too many element seperators");
        }

        PulseMessage pulseMessage = new PulseMessage(data);
        return pulseMessage;
    }
}
