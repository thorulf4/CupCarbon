package d504;

import d504.utils.Serialize;

public class AckMessage {

    private String messageID;

    public AckMessage (String messageID){
        if(messageID.isEmpty()){
            throw new RuntimeException("MessageId can´t be empty");
        }
        this.messageID = messageID;
    }

    public String serialize(){
        return messageID;
    }

    public static AckMessage deserialize(String message){
        if(message.isEmpty()){
            throw new RuntimeException("Message can't be empty");
        }
        return new AckMessage(message);
    }

    public String getMessageId() {
        return messageID;
    }
}
