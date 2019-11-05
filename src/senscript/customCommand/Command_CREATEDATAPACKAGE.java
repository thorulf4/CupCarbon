package senscript.customCommand;

import d504.DataPackage;
import d504.ISensorNode;
import d504.PackageType;
import d504.TypedPackage;



import java.util.Random;


public class Command_CREATEDATAPACKAGE extends Command {

    private String output;
    private String message;

    public Command_CREATEDATAPACKAGE(ISensorNode sensor, String message, String output){
        this.sensor = sensor;
        this.message = message;
        this.output = output;
    }


    @Override
    public double execute(){
        Random idGen = new Random();
        int ID = 10000000 + idGen.nextInt(90000000);
        DataPackage dataPackage = new DataPackage(Integer.toString(ID), String.valueOf(sensor.getId()), message);
        TypedPackage typedPackage = new TypedPackage(PackageType.Data, Integer.toString(sensor.getId()), dataPackage.serialize());
        sensor.putVariable(output,typedPackage.serialize());
        return 0;

    }
}
