package d504;


public interface ISensorNode {

    int getId();
    String getVariableValue(String variableName);
    void putVariable(String variableName, String value);
    double getSimulationTime();
}
