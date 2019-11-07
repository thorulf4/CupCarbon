package senscript.customCommand;

import d504.ISensorNode;

public class Command_TIMEOUT extends Command {

    private String isTimeOut;
    private String timerVariable;
    private String timeLimit;

    public Command_TIMEOUT(ISensorNode sensorNode, String timerVariable, String timeLimit , String isTimeOut){
        this.sensor = sensorNode;
        this.timerVariable = timerVariable;
        this.timeLimit = timeLimit;
        this.isTimeOut = isTimeOut;
    }

   public Command_TIMEOUT(ISensorNode sensorNode, String timerVariable, String isTimeOut){
        this.sensor = sensorNode;
        this.timerVariable = timerVariable;
        this.isTimeOut = isTimeOut;
    }

    @Override
    public double execute() {
        double timeInterval;
        if(timeLimit == null || timeLimit.isEmpty()){
            timeInterval = 3000;
        } else{
            timeInterval = Double.parseDouble(timeLimit);
        }

        double now = sensor.getSimulationTime();
        String timeData = sensor.getVariableValue(timerVariable);
        double lastTime = Double.parseDouble(timeData);
        double timeSpent = now - lastTime;


        if (timeSpent >= timeInterval){

            sensor.putVariable(isTimeOut, "true");
        } else{
            sensor.putVariable(isTimeOut, "false");
        }
        return 0;
    }
}
