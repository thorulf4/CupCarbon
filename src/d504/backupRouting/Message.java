package d504.backupRouting;

import d504.DataPackage;
import d504.utils.Serialize;

import java.util.ArrayList;
import java.util.List;

class Message {
    public String sender;
    public List<String> receivers;
    public DataPackage dataPackage;

    public Message(String sender, DataPackage dataPackage) {
        this.sender = sender;
        this.dataPackage = dataPackage;
        receivers = new ArrayList<>();
    }

    private Message() {
        receivers = new ArrayList<>();
    }

    public String serialize(){
        StringBuilder builder = new StringBuilder();
        builder.append(sender);
        builder.append("&");
        builder.append(dataPackage.serialize());
        for (String receiver : receivers) {
            builder.append("&");
            builder.append(receiver);
        }

        return builder.toString();
    }

    public static Message deserialize(String serializedMessage){
        Message message = new Message();
        message.sender = Serialize.nextElement(serializedMessage);
        serializedMessage = Serialize.removeElements(serializedMessage, 1);

        message.dataPackage = DataPackage.deserialize(Serialize.getSeqment(serializedMessage, 3));
        serializedMessage = Serialize.removeElements(serializedMessage, 3);

        String[] receivers = serializedMessage.split("&");
        for (String receiver : receivers) {
            message.receivers.add(receiver);
        }

        return message;
    }
}
