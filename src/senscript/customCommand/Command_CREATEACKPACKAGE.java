package senscript.customCommand;

import d504.*;


public class Command_CREATEACKPACKAGE extends Command {

    private String dataPackageVariable;
    private String ackPackageOutput;


    public Command_CREATEACKPACKAGE(ISensorNode sensor, String dataPackageVariable, String ackPacakgeOutput){
        this.sensor = sensor;
        this.dataPackageVariable = dataPackageVariable;
        this.ackPackageOutput = ackPacakgeOutput;
    }


    @Override
    public double execute(){
        String serializedDataPackage = sensor.getVariableValue(dataPackageVariable);
        DataPackage dataPackage = DataPackage.deserialize(serializedDataPackage);
        String messageID = dataPackage.getMessageID();
        AckMessage ackMessage = new AckMessage(Integer.toString(sensor.getId()),messageID);
        TypedPackage typedPackage = new TypedPackage(PackageType.Ack, Integer.toString(sensor.getId()), ackMessage.serialize());
        sensor.putVariable(ackPackageOutput,typedPackage.serialize());
        return 0;
    }

}
