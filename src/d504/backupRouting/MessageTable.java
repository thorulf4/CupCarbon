package d504.backupRouting;

import d504.DataPackage;
import d504.utils.Serialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MessageTable {

    private HashMap<String, Message> messages;

    public MessageTable() {
        messages = new HashMap<>();
    }

    public boolean isMessagePresent(String messageId){
        return messages.containsKey(messageId);
    }

    public void addMessage(String messageId, String sender, DataPackage dataPackage){
        Message message = new Message(sender, 2, dataPackage);
        messages.put(messageId, message);
    }

    public void addReceiver(String messageId, String receiver){
        messages.get(messageId).receivers.add(receiver);
    }

    public String getSender(String messageId){
        return messages.get(messageId).sender;
    }

    public DataPackage getDataPackage(String messageId){
        return messages.get(messageId).dataPackage;
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

            int elementCount = 3 + 3 + messages.get(messageId).receivers.size();
            int elementCount = 2 + 3 + messages.get(messageId).receivers.size();
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
        }


    }

    public boolean isCongaActive(String messageId){
        Message message = messages.get(messageId);

        return message.congaStepsLeft > 0;
    }

}
