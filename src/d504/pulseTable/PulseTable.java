package d504.pulseTable;

import java.util.*;

public class PulseTable {

    private HashMap<String, Integer> neighbourTicksLeft;
    private int maxTicks;


    public PulseTable(int maxTicks) {
        neighbourTicksLeft = new HashMap<>();
        this.maxTicks = maxTicks;
    }

    public void pulseNeighbour(String neighbourId){
        if(neighbourTicksLeft.containsKey(neighbourId)){
            neighbourTicksLeft.replace(neighbourId, maxTicks);
        }else{
            neighbourTicksLeft.put(neighbourId, maxTicks);
        }
    }

    public void tickAllNeighbours(){
        for (String key : neighbourTicksLeft.keySet()) {
            int newTickCount = neighbourTicksLeft.get(key);
            neighbourTicksLeft.replace(key, newTickCount);
        }
    }

    public List<String> getDeadNeighbours(){
        List<String> deadNeighbours = new ArrayList<>();
        for (String key : neighbourTicksLeft.keySet()) {
            if(neighbourTicksLeft.get(key) <= 0)
                deadNeighbours.add(key);
        }
        return deadNeighbours;
    }


}
