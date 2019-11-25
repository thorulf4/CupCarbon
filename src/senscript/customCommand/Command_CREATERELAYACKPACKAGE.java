package senscript.customCommand;

import d504.*;

public class Command_CREATERELAYACKPACKAGE extends Command {

    private String dataVariable;
    private String ackPackageVariable;

    public Command_CREATERELAYACKPACKAGE(ISensorNode sensorNode, String dataVariable, String ackPackageVariable) {
        this.sensor = sensorNode;
        this.dataVariable = dataVariable;
        this.ackPackageVariable = ackPackageVariable;
    }

    @Override
    public double execute() {
        DataPackage dataPackage = getData();

        AckMessage ack = new AckMessage(dataPackage.getMessageID());
        TypedPackage typedPackage = new TypedPackage(PackageType.RelayAck, String.valueOf(sensor.getId()), ack.serialize());

        sensor.putVariable(ackPackageVariable, typedPackage.serialize());

        return 0;
    }

    private DataPackage getData() {
        String serializedData = sensor.getVariableValue(dataVariable);
        return DataPackage.deserialize(serializedData);
    }
}
