package senscript.customCommand;

import d504.ISensorNode;
import d504.TypedPackage;



public class Command_DECIPHER extends Command {

    private String inputPacket;
    private String packageVariable;
    private ISensorNode sensorNode;
    private String typeVariable;
    private String senderVariable;

    public  Command_DECIPHER(ISensorNode sensorNode,String inputPacket, String packageVariable, String typeVariable, String senderVariable){
        this.sensorNode = sensorNode;
        this.packageVariable = packageVariable;
        this.typeVariable = typeVariable;
        this.inputPacket = inputPacket;
        this.senderVariable = senderVariable;
    }

    @Override
    public double execute() {
        String packageData =  sensorNode.getVariableValue(inputPacket);
        TypedPackage typedPackage = TypedPackage.deserialize(packageData);

        sensorNode.putVariable(packageVariable, typedPackage.packageData);
        sensorNode.putVariable(typeVariable, typedPackage.type.ordinal()+"");
        sensorNode.putVariable(senderVariable, typedPackage.nodeID);

        return 0;
    }
}
