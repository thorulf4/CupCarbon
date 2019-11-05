package d504;

import senscript.SenScript;

import java.util.HashMap;

public class TestableSensorNode implements ISensorNode {

    private int id;
    private HashMap<String, String> variables;
    private double time;

    public TestableSensorNode(int id) {
        this.id = id;
        variables = new HashMap<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void putVariable(String var, String s) {
        variables.put(var, s);
    }

    @Override
    public double getSimulationTime() {
        return time;
    }

    public void addTime(double time){
        this.time += time;
    }


    @Override
    public String getVariableValue(String arg) {
        if (arg.equals("\\"))
            return "";
        if (arg.equals(""))
            return "";
        if(arg.charAt(0)=='$')
            return variables.get(arg.substring(1));
        return arg;
    }
}
