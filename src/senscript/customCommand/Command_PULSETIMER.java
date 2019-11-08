package senscript.customCommand;


import d504.ISensorNode;
import simulation.WisenSimulation;

public class Command_PULSETIMER extends Command {

    private String timerVariable;
    private String intervalInHours;
    private String outputShouldTick;

    public Command_PULSETIMER(ISensorNode sensorNode, String timerVariable, String intervalInHours, String outputShouldTick) {
        this.sensor = sensorNode;
        this.timerVariable = timerVariable;
        this.intervalInHours = intervalInHours;
        this.outputShouldTick = outputShouldTick;
    }

    @Override
    public double execute() {
        String timeData = sensor.getVariableValue(timerVariable);
        double lastTime = Double.parseDouble(timeData);
        double now = sensor.getSimulationTime();
        double intervalTime = getIntervalTime();

        double deltaTime = now - lastTime;
        if(deltaTime >= intervalTime){
            sensor.putVariable(outputShouldTick, "true");

            updateTimer(now + deltaTime - intervalTime);
        }else{
            sensor.putVariable(outputShouldTick, "false");
        }

        return 0;
    }

    private void updateTimer(double newTime) {
        sensor.putVariable(timerVariable.substring(1), "" + newTime);
    }

    private double getIntervalTime() {
        //Convert time to seconds
        return Double.parseDouble(sensor.getVariableValue(intervalInHours)) * 3600;
    }


}
