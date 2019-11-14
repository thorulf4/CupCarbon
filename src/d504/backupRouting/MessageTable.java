package d504.backupRouting;

import d504.DataPackage;
import d504.exceptions.MessageNotFoundException;
import d504.utils.Serialize;

import java.util.*;
import java.util.stream.Collectors;

public class MessageTable {

    private static final int CONGA_STEPS = 2;

    private HashMap<String, Message> messages;

    public MessageTable() {
        messages = new HashMap<>();
    }

    public boolean isMessagePresent(String messageId){
        return messages.containsKey(messageId);
    }

    public void addMessage(long expirationTime, String sender, DataPackage dataPackage){
        Message message = new Message(sender, CONGA_STEPS, expirationTime, dataPackage);
        message.setTimerTimeLeft(CONGA_STEPS);
        messages.put(dataPackage.getMessageID(), message);
    }

    public void addReceiver(String messageId, String receiver){
        messages.get(messageId).receivers.add(receiver);
    }

    public String getSender(String messageId){
        return messages.get(messageId).sender;
    }

    public DataPackage getDataPackage(String messageId){
        Optional<DataPackage> optionalDataPackage = messages.entrySet().stream()
                .filter(e -> e.getKey().equals(messageId))
                .map(e -> e.getValue().dataPackage)
                .findFirst();

        if(optionalDataPackage.isPresent()){
            return optionalDataPackage.get();
        }else{
            throw new MessageNotFoundException("MessageId: " + messageId + " was not found in MessageTable");
        }
    }

    public List<String> getReceivers(String messageId){
        return new ArrayList<>(messages.get(messageId).receivers);
    }

    public void removeMessage(String messageId){
        messages.remove(messageId);
    }

    public String serialize(){
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> keySet = messages.keySet().iterator();

        for (int i = 0 ; i < messages.keySet().size(); i++) {
            if(i!=0)
                stringBuilder.append("&");

            String messageId = keySet.next();

            int elementCount = 4 + 3 + messages.get(messageId).receivers.size();
            stringBuilder.append(messageId);
            stringBuilder.append("&");
            stringBuilder.append(elementCount);
            stringBuilder.append("&");
            stringBuilder.append(messages.get(messageId).serialize());
        }

        return stringBuilder.toString();
    }

    public static MessageTable deserialize(String serializedMessageTable){
        MessageTable messageTable = new MessageTable();
        while(!serializedMessageTable.equals("")){
            String[] serializedMetaData = Serialize.nextElements(serializedMessageTable, 2);
            serializedMessageTable = Serialize.removeElements(serializedMessageTable, 2);

            String messageId = serializedMetaData[0];
            int elementCount = Integer.parseInt(serializedMetaData[1]);

            String serializedMessage = Serialize.getSeqment(serializedMessageTable, elementCount);
            serializedMessageTable = Serialize.removeElements(serializedMessageTable, elementCount);

            Message message = Message.deserialize(serializedMessage);

            messageTable.messages.put(messageId, message);
        }

        return messageTable;
    }

    public void decreaseCongaStep(String messageId){
        Message message = messages.get(messageId);
        if(message.congaStepsLeft > 0){
            message.congaStepsLeft--;
            if(message.congaStepsLeft != 0){
                message.setTimerTimeLeft((CONGA_STEPS + 1 - message.congaStepsLeft));
            }else{
                message.disableTimer();
            }
        }
    }

    public boolean isCongaActive(String messageId){
        Message message = messages.get(messageId);

        return message.congaStepsLeft > 0;
    }

    public void tickTimers(double timeStep) {
        messages.forEach((id, m) -> m.tickTimer(timeStep));
    }

    public List<String> getTimedOutMessages() {
        return messages.entrySet().stream()
                .filter(e -> e.getValue().getTimerTimeLeft() <= 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void tickExpirationTimers(long currentTime){
        List<String> expiredKeys = new ArrayList<>();

        for (String key : messages.keySet()) {
            Message message = messages.get(key);
            if(message.expiryTime < currentTime)
                expiredKeys.add(key);
        }

        for (String key : expiredKeys) {
            messages.remove(key);
        }
    }
}
