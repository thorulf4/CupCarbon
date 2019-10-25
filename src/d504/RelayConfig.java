package d504;

public class RelayConfig {

    String relayID="";
    int jumps=0;


    public RelayConfig (String id, String jump){
        relayID = id;
        jumps = Integer.parseInt(jump);
    }

    public String serialize(){
        StringBuilder serializedRelayConfig = new StringBuilder();

        serializedRelayConfig.append(relayID).append("&").append(jumps);
        return serializedRelayConfig.toString();
    }

    public static RelayConfig deserialize(String input){
        String splitInput[] = input.split("&");
        return new RelayConfig(splitInput[0], splitInput[1]);
    }



}
