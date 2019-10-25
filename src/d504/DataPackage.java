package d504;

public class DataPackage{

    private String targetRelay;
    private String data;

    public DataPackage(String targetRelay, String data){
        this.targetRelay = targetRelay;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getTargetRelay() {
        return targetRelay;
    }

    public String serialize(){
        StringBuilder serialPackage = new StringBuilder();
        serialPackage.append(targetRelay).append("#").append(data);
        return serialPackage.toString();
    }

    public static DataPackage deserialize(String input){

        String splitInput[]=input.split("#");

        return new DataPackage(splitInput[0],splitInput[1]);
    }
}
