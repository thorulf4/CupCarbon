package senscript.customCommand;

import device.SensorNode;
import senscript.Command;
import simulation.WisenSimulation;

public class Command_PULSETIMER extends Command {

    private String timerVariable;
    private String intervalInHours;
    private String outputShouldTick;

    public Command_PULSETIMER(SensorNode sensorNode, String timerVariable, String intervalInHours, String outputShouldTick) {
        this.sensor = sensorNode;
        this.timerVariable = timerVariable;
        this.intervalInHours = intervalInHours;
        this.outputShouldTick = outputShouldTick;
    }

    @Override
    public double execute() {
        String timeData = sensor.getScript().getVariableValue(timerVariable);
        double lastTime = Double.parseDouble(timeData);
        double now = getSimulationTime();
        double intervalTime = getIntervalTime();

        double deltaTime = now - lastTime;
        if(deltaTime >= intervalTime){
            sensor.getScript().putVariable(outputShouldTick, "true");

            updateTimer(now + deltaTime - intervalTime);
        }else{
            sensor.getScript().putVariable(outputShouldTick, "false");
        }

        return 0;
    }

    private void updateTimer(double newTime) {
        sensor.getScript().putVariable(timerVariable.substring(1), "" + newTime);
    }

    private double getIntervalTime() {
        //Convert time to seconds
        return Double.parseDouble(intervalInHours) * 3600;
    }

    private double getSimulationTime(){
        return WisenSimulation.time * sensor.getDriftTime();
    }
}
