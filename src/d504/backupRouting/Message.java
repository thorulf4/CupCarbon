package d504.backupRouting;

import d504.DataPackage;
import d504.utils.Serialize;

import java.util.ArrayList;
import java.util.List;

class Message {
    public String sender;
    public List<String> receivers;
    public double timerTimeLeft;
    public DataPackage dataPackage;
    public int congaStepsLeft = 0;

    public Message(String sender, int congaStepsLeft, DataPackage dataPackage) {
        this.congaStepsLeft = congaStepsLeft;
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
        builder.append(congaStepsLeft);
        builder.append("&");
        builder.append(timerTimeLeft);
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
        message.congaStepsLeft = Integer.parseInt(Serialize.nextElement(serializedMessage));
        serializedMessage = Serialize.removeElements(serializedMessage, 1);
        message.timerTimeLeft = Double.parseDouble(Serialize.nextElement(serializedMessage));
        serializedMessage = Serialize.removeElements(serializedMessage, 1);

        message.dataPackage = DataPackage.deserialize(Serialize.getSeqment(serializedMessage, 3));
        serializedMessage = Serialize.removeElements(serializedMessage, 3);

        if(!serializedMessage.isEmpty()){
            String[] receivers = serializedMessage.split("&");
            for (String receiver : receivers) {
                message.receivers.add(receiver);
            }
        }

        return message;
    }
}
