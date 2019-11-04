package d504;

import d504.utils.Serialize;

public class AckMessage {

    private String senderID;
    private String messageID;

    public AckMessage (String senderID, String messageID){
        this.senderID = senderID;
        this.messageID = messageID;
    }

    public String serialize(){
        StringBuilder ackSerialized = new StringBuilder();
        ackSerialized.append(senderID).append("&").append(messageID);
        return ackSerialized.toString();
    }

    public static AckMessage deserialize(String message){
        String[] ackMessage = Serialize.nextElements(message, 2);
        return new AckMessage(ackMessage[0],ackMessage[1]);

    }

    public String getMessageId() {
        return messageID;
    }
}
