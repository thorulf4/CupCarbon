package d504.utils;

public final class Serialize {
    public static final String seperator = "&";

    public static String nextElement(String data){
        return data.substring(0, data.indexOf(seperator));
    }

    public static String[] nextElements(String data, int count){
        String[] elements = new String[count];

        int from = 0;
        for(int i = 0; i < count; i++){
            int index = data.indexOf(seperator, from);

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
            int index = data.indexOf(seperator, from);
            from = index + 1;
        }

        return data.substring(from);
    }


}
