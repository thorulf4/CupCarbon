package senscript.customCommand;


import d504.ISensorNode;

public class Command_BIGTEST extends Command {

    String word;

    public Command_BIGTEST(ISensorNode sensorNode, String arg1) {
        word = arg1;
    }

    @Override
    public double execute() {
        throw new RuntimeException("hey");

        //System.out.println("My test worked: " + word);
        //return 0;
    }
}
