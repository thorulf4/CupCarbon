package senscript.customCommand;

import d504.ISensorNode;
import d504.utils.Serialize;



public class Command_GETNEXTRECEIVER extends Command {

    private String receiverListVar;
    private String outputReveicer;
    private String moreleft;



    public Command_GETNEXTRECEIVER(ISensorNode sensor, String receiverVar, String outputReceiver, String moreLeft){
        this.sensor = sensor;
        this.receiverListVar = receiverVar;
        this.outputReveicer = outputReceiver;
        this.moreleft = moreLeft;

    }


    @Override
    public double execute(){
        String listVar = sensor.getVariableValue("$"+receiverListVar);
        String nextRe = Serialize.nextElement(listVar);
        String notEmpty;
        listVar = Serialize.removeElements(listVar,1);
        if (listVar.equals(""))
            notEmpty = Boolean.toString(false);
        else
            notEmpty = Boolean.toString(true);
        sensor.putVariable(receiverListVar, listVar);
        sensor.putVariable(moreleft, notEmpty);
        sensor.putVariable(outputReveicer,nextRe);
        return 0;

    }
}
