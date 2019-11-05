package senscript.customCommand;

import d504.ISensorNode;

public abstract class Command {
    protected ISensorNode sensor;

    public abstract double execute();
}
