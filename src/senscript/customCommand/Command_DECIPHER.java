package senscript.customCommand;

import d504.TypedPackage;
import device.SensorNode;
import senscript.Command;

public class Command_DECIPHER extends Command {

    private String inputPacket;
    private String packageVariable;
    private SensorNode sensorNode;
    private String typeVariable;
    private String senderVariable;

    public  Command_DECIPHER(SensorNode sensorNode,String inputPacket, String packageVariable, String typeVariable, String senderVariable){
        this.sensorNode = sensorNode;
        this.packageVariable = packageVariable;
        this.typeVariable = typeVariable;
        this.inputPacket = inputPacket;
        this.senderVariable = senderVariable;
    }

    @Override
    public double execute() {
        String packageData =  sensorNode.getScript().getVariableValue(inputPacket);
        TypedPackage typedPackage = TypedPackage.deserialize(packageData);

        sensorNode.getScript().putVariable(packageVariable, typedPackage.packageData);
        sensorNode.getScript().putVariable(typeVariable, typedPackage.type.ordinal()+"");
        sensorNode.getScript().putVariable(senderVariable, typedPackage.nodeID);

        return 0;
    }
}
