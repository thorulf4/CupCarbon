package d504.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class Serialize {
    public static final String separator = "&";

    public static String nextElement(String data){
        return nextElements(data, 1)[0];
    }

    public static String getSeqment(String data, int count) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] elements = nextElements(data, count);

        for(int i = 0; i<count; i++){
            if(i != 0)
                stringBuilder.append("&");

            stringBuilder.append(elements[i]);
        }

        return stringBuilder.toString();
    }

    public static String[] nextElements(String data, int count){
        String[] elements = new String[count];

        int from = 0;
        for(int i = 0; i < count; i++){
            int index = data.indexOf(separator, from);

            if(index == -1){
                elements[i] = data.substring(from);
            }else{
                elements[i] = data.substring(from, index);
            }

            from = index + 1;
        }

        return elements;
    }

    public static String removeElements(String data, int amount){
        int from = 0;
        for(int i = 0; i < amount; i++){
            int index = data.indexOf(separator, from);

            if(index == -1)
                return "";

            from = index + 1;
        }

        return data.substring(from);
    }

    public static String serialize(List<String> elements){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < elements.size(); i++){
            if(i != 0)
                stringBuilder.append('&');

            stringBuilder.append(elements.get(i));
        }

        return stringBuilder.toString();
    }

    public static List<String> deserializeStringList(String serializedList){
        if(serializedList.equals(""))
            return new ArrayList<>();

        String[] elements = serializedList.split("&");
        return Arrays.asList(elements);
    }
}
