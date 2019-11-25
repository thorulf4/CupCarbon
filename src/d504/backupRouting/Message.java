package d504.backupRouting;

import d504.DataPackage;
import d504.utils.Serialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Message {
    private double timerTimeLeft;
    private List<String> receivers;

    public String sender;
    public DataPackage dataPackage;
    public int congaStepsLeft;
    public long expiryTime;

    public Message(String sender, int congaSteps, long expiryTime, DataPackage dataPackage) {
        this.congaStepsLeft = congaSteps;
        this.sender = sender;
        this.dataPackage = dataPackage;
        this.expiryTime = expiryTime;
        receivers = new ArrayList<>();
    }

    private Message() {
        receivers = new ArrayList<>();
    }

    public void addReceiver(String nodeId){
        receivers.add(nodeId);
    }

    public List<String> getReceivers() {
        return Collections.unmodifiableList(receivers);
    }

    public String serialize(){
        StringBuilder builder = new StringBuilder();
        builder.append(sender);
        builder.append("&");
        builder.append(expiryTime);
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
        message.expiryTime = Long.parseLong(Serialize.nextElement(serializedMessage));
        serializedMessage = Serialize.removeElements(serializedMessage, 1);
        message.congaStepsLeft = Integer.parseInt(Serialize.nextElement(serializedMessage));
        serializedMessage = Serialize.removeElements(serializedMessage, 1);
        message.timerTimeLeft = Double.parseDouble(Serialize.nextElement(serializedMessage));
        serializedMessage = Serialize.removeElements(serializedMessage, 1);

        message.dataPackage = DataPackage.deserialize(Serialize.getSeqment(serializedMessage, 3));
        serializedMessage = Serialize.removeElements(serializedMessage, 3);

        if(!serializedMessage.isEmpty()){
            String[] receivers = serializedMessage.split("&");
            message.receivers.addAll(Arrays.asList(receivers));
        }

        return message;
    }

    public double getTimerTimeLeft() {
        return timerTimeLeft;
    }

    public void setTimerTimeLeft(double timerTimeLeft) {
        if(timerTimeLeft < 0)
            throw new RuntimeException("Timer can´t be set to a negative value. Use disableTimer instead");
        this.timerTimeLeft = timerTimeLeft;
    }

    public void disableTimer(){
        timerTimeLeft = Double.MIN_VALUE;
    }


    public void tickTimer(double timeStep) {
        if(timerTimeLeft == Double.MIN_VALUE)
            return;

        if(timerTimeLeft < 0){
            disableTimer();
        }else{
            timerTimeLeft -= timeStep;
        }
    }
}
