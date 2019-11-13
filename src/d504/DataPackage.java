package d504;

import d504.utils.Serialize;

import java.util.Random;

public class DataPackage{

    private String messageID;
    private String targetRelay;
    private String data;

    public DataPackage(String messageID, String targetRelay, String data){
        this.messageID = messageID;
        this.targetRelay = targetRelay;
        this.data = data;
    }

    public DataPackage(String targetRelay, String data) {
        Random idGen = new Random();
        this.messageID = String.valueOf(10000000 + idGen.nextInt(90000000));
        this.targetRelay = targetRelay;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getMessageID(){
        return messageID;
    }

    public String getTargetRelay() {
        return targetRelay;
    }

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(messageID).append("&").append(targetRelay).append("&").append(data);
        return stringBuilder.toString();
    }

    public static DataPackage deserialize(String input){
        String[] inputMessage = Serialize.nextElements(input,3);
        return new DataPackage(inputMessage[0],inputMessage[1], inputMessage[2]);
    }
}
