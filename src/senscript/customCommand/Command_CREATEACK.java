package senscript.customCommand;

import d504.*;

public class Command_CREATEACK extends Command {

    private String dataVariable;
    private String outputAck;

    public Command_CREATEACK(ISensorNode sensorNode, String dataVariable, String outputAck) {
        this.sensor = sensorNode;
        this.dataVariable = dataVariable;
        this.outputAck = outputAck;
    }

    @Override
    public double execute() {
        DataPackage data = getDataPackage();

        AckMessage ack = new AckMessage(data.getMessageID());
        TypedPackage typedPackage = new TypedPackage(PackageType.Ack, String.valueOf(sensor.getId()), ack.serialize());

        sensor.putVariable(outputAck, ack.serialize());

        return 0;
    }

    private DataPackage getDataPackage() {
        String serializedData = sensor.getVariableValue(dataVariable);
        return DataPackage.deserialize(serializedData);
    }
}
