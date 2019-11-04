package senscript.customCommand;

import d504.ConfigPackage;
import d504.TypedPackage;
import device.SensorNode;
import senscript.Command;

import static d504.PackageType.Config;

public class Command_CREATERELAYCONFIG extends Command {

    String output;

    public Command_CREATERELAYCONFIG(SensorNode node, String output){
    this.sensor = node;
    this.output = output;
    }


    @Override
    public double execute(){
        String id = Integer.toString(sensor.getId());
        ConfigPackage configPackage = new ConfigPackage(id);
        configPackage.add(id,1);
        String serializedConfigPackage = configPackage.serialize();
        TypedPackage typedRelayConfig = new TypedPackage(Config, id, serializedConfigPackage);
        String serializedTypedConfig = typedRelayConfig.serialize();
        sensor.getScript().putVariable(output,serializedTypedConfig);
        return 0;
    }
}
