package senscript.customCommand;

import d504.ISensorNode;

public abstract class Command {
    protected ISensorNode sensor;
    public int lineNumber;

    public abstract double execute();
}
