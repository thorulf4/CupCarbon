package d504.pulseTable;

import d504.utils.Serialize;

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
            neighbourTicksLeft.replace(key, newTickCount - 1);
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

    public String serialize(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(maxTicks);
        for (String key : neighbourTicksLeft.keySet()) {
            stringBuilder.append('&');
            stringBuilder.append(key);
            stringBuilder.append('&');

            int ticks = neighbourTicksLeft.get(key);
            stringBuilder.append(ticks);
        }

        return stringBuilder.toString();
    }

    public static PulseTable deserialize(String data){
        int maxTicks = Integer.parseInt(Serialize.nextElement(data));
        data = Serialize.removeElements(data, 1);

        PulseTable pulseTable = new PulseTable(maxTicks);

        while(!data.equals("")){


            String[] elements = Serialize.nextElements(data, 2);
            data = Serialize.removeElements(data, 2);

            int ticks = Integer.parseInt(elements[1]);

            pulseTable.neighbourTicksLeft.put(elements[0], ticks);
        }

        return pulseTable;
    }


}
