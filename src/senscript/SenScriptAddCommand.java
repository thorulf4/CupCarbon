package senscript;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

import device.SensorNode;

public final class SenScriptAddCommand {

	public static Stack<String> endof = new Stack<String>();
	
	public static String detectKeyWord(String s) {
		if(!s.startsWith("set "))
			return s.replaceFirst("\\(", " (");
		return s;
	}
	
	public static void addCommand(String instStr, SensorNode sensorNode, SenScript script) {
		instStr = detectKeyWord(instStr);
		String[] inst = instStr.split(" ");
		
		if(inst[0].split(":").length>1) {
			sensorNode.getScript().addLabel(inst[0].split(":")[0], sensorNode.getScript().size()+1);			
			inst[0] = inst[0].split(":")[1];
		}

		String commandName = inst[0].toLowerCase();
		Command command = null;

		try{
			String packageName = "senscript.customCommand";
			Class<?> commandClass = ClassLoader.getSystemClassLoader().loadClass(packageName + ".Command_" + commandName.toUpperCase());

			ArrayList<Class<?>> parameterTypes = new ArrayList<>();
			ArrayList<String> parameters = new ArrayList<>();

			for (int i = 1; i < inst.length; i++) {
				parameterTypes.add(String.class);
				parameters.add(inst[i]);
			}

			command  = (Command) commandClass.getConstructor(parameterTypes.toArray(new Class<?>[]{})).newInstance(parameters.toArray(new Object[]{}));
		}catch (Exception e){}



		if (commandName.equals("end")) {
			instStr = endof.pop();
			addCommand(instStr, sensorNode, script);
		}
		
		if (commandName.equals("simulation")) {
			command = new Command_SIMULATION(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("script")) {
			command = new Command_SCRIPT(sensorNode, inst[1]);
		}
		if (commandName.equals("rscript")) {
			command = new Command_RSCRIPT(sensorNode, inst[1]);
		}
		if (commandName.equals("kill")) {
			command = new Command_KILL(sensorNode, inst[1]);
		}
		if (commandName.equals("delay")) {
			command = new Command_DELAY(sensorNode, inst[1]);
		}				
		if (commandName.equals("set")) {
			command = new Command_SET(sensorNode, inst[1], inst[2]);
		}	
		if (commandName.equals("read")) {
			command = new Command_READ(sensorNode, inst[1]);
		}
		if (commandName.equals("cbuffer")) {
			command = new Command_CBUFFER(sensorNode);
		}
		if (commandName.equals("scolor")) {
			command = new Command_SCOLOR(sensorNode, inst[1]);
		}
		if (commandName.equals("send")) {
			if(inst.length==2) command = new Command_SEND(sensorNode, inst[1], "*");
			if(inst.length==3) command = new Command_SEND(sensorNode, inst[1], inst[2]);
			if(inst.length==4) command = new Command_SEND(sensorNode, inst[1], inst[2], inst[3]);
			if(inst.length==5) command = new Command_SEND(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		if (commandName.equals("loop")) {
			command = new Command_LOOP(sensorNode);
		}
		if (commandName.equals("wait")) {
			if(inst.length==1) command = new Command_WAIT(sensorNode);
			if(inst.length==2) command = new Command_WAIT(sensorNode, inst[1]);
		}
		if (commandName.equals("receive")) {
			command = new Command_RECEIVE(sensorNode, inst[1]);
		}
		if (commandName.equals("stop")) {
			command = new Command_STOP(sensorNode);
		}
		
		if (commandName.equals("xor")) {
			command = new Command_XOR(sensorNode, inst[1], inst[2], inst[3]);
		}
		
		if (commandName.equals("and")) {
			command = new Command_AND(sensorNode, inst[1], inst[2], inst[3]);
		}
		
		if (commandName.equals("or")) {
			command = new Command_OR(sensorNode, inst[1], inst[2], inst[3]);
		}
		
		if (commandName.equals("not")) {
			command = new Command_NOT(sensorNode, inst[1], inst[2]);
		}
		
		if (commandName.equals("bxor")) {
			command = new Command_BXOR(sensorNode, inst[1], inst[2], inst[3]);
		}
				
		if (commandName.equals("band")) {
			command = new Command_BAND(sensorNode, inst[1], inst[2], inst[3]);
		}
		
		if (commandName.equals("bor")) {
			command = new Command_BOR(sensorNode, inst[1], inst[2], inst[3]);
		}
		
		if (commandName.equals("bnot")) {
			command = new Command_BNOT(sensorNode, inst[1], inst[2]);
		}		

		if (commandName.equals("hash")) {
			command = new Command_HASH(sensorNode, inst[1], inst[2]);
		}		
		
		if (commandName.equals("plus")) {
			command = new Command_PLUS(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("minus")) {
			command = new Command_MINUS(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("mult")) {
			command = new Command_MULT(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("div")) {
			command = new Command_DIV(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("mod")) {
			command = new Command_MOD(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("tab")) {
			command = new Command_TAB(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("vec")) {
			command = new Command_VEC(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("atnd")) {
			if(inst.length==2) command = new Command_ATND(sensorNode, inst[1]);
			if(inst.length==3) command = new Command_ATND(sensorNode, inst[1], inst[2]);
		}
		
		if (commandName.equals("route")) {
			command =  new Command_ROUTE(sensorNode, inst[1]);
		}
		
		if (commandName.equals("println")) {
			command =  new Command_PRINT(sensorNode, inst);
		}
		
		if (commandName.equals("print")) {
			command =  new Command_PRINT(sensorNode, inst);
		}
		
		if (commandName.equals("printfile")) {
			command =  new Command_PRINTFILE(sensorNode, inst);
		}
		
		if (commandName.equals("data")) {
			command = new Command_DATA(sensorNode, inst);
		}
		if (commandName.equals("rdata")) {
			command = new Command_RDATA(sensorNode, inst);
		}
		if (commandName.equals("last")) {
			command = new Command_LAST(sensorNode, inst);
		}
		if (commandName.equals("wlast")) {
			command = new Command_WLAST(sensorNode, inst);
		}
		if (commandName.equals("first")) {
			command = new Command_FIRST(sensorNode, inst);
		}
		if (commandName.equals("wfirst")) {
			command = new Command_WFIRST(sensorNode, inst);
		}
		if (commandName.equals("nth")) {
			command = new Command_NTH(sensorNode, inst);
		}
		if (commandName.equals("sadd")) {
			command = new Command_SADD(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("spop")) {
			command = new Command_SPOP(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("vdata")) {
			command = new Command_VDATA(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("charat")) {
			command = new Command_CHARAT(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("length")) {
			command = new Command_LENGTH(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("angle")) {
			command = new Command_ANGLE(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		if (commandName.equals("angle2")) {
			command = new Command_ANGLE2(sensorNode, inst[1], inst[2], inst[3], inst[4], inst[5], inst[6], inst[7]);
		}
		if (commandName.equals("edge")) {
			command = new Command_EDGE(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("mark")) {
			command = new Command_MARK(sensorNode, inst[1]);
		}
		if (commandName.equals("mmin")) {
			command = new Command_MMIN(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		if (commandName.equals("smin")) {
			command = new Command_SMIN(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("smax")) {
			command = new Command_SMAX(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("min")) {
			command = new Command_MIN(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("max")) {
			command = new Command_MAX(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("getpos2")) {
			command = new Command_GETPOS2(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("buffer")) {
			command = new Command_BUFFER(sensorNode, inst[1]);
		}
		if (commandName.equals("battery")) {
			if(inst.length==2) command = new Command_BATTERY(sensorNode, inst[1]);
			if(inst.length==3) command = new Command_BATTERY(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("getpos")) {
			command = new Command_GETPOS(sensorNode, inst[1]);
		}
		if (commandName.equals("atget")) {
			command = new Command_ATGET(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("radio")) {
			command = new Command_RADIO(sensorNode, inst[1]);
		}
		if (commandName.equals("rand")) {
			command = new Command_RAND(sensorNode, inst[1]);
		}
		if (commandName.equals("rgauss")) {
			command = new Command_RGAUSS(sensorNode, inst[1]);
		}
		if (commandName.equals("led")) {
			command = new Command_LED(sensorNode, inst[1], inst[2]);
		}
		if (commandName.equals("randb")) {
			command = new Command_RANDB(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("math")) {
			if(inst.length==4) command = new Command_MATH(sensorNode, inst[1], inst[2], inst[3]);
			if(inst.length==5) command = new Command_MATH(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		if (commandName.equals("tset")) {
			command = new Command_TSET(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		if (commandName.equals("vset")) {
			command = new Command_VSET(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("tget")) {
			command = new Command_TGET(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		if (commandName.equals("vget")) {
			command = new Command_VGET(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("dreadsensor")) {
			command = new Command_DREADSENSOR(sensorNode, inst[1]);
		}
		if (commandName.equals("areadsensor")) {
			command = new Command_AREADSENSOR(sensorNode, inst[1]);
		}
		if (commandName.equals("function")) {
			command = new Command_FUNCTION(sensorNode, inst[1], inst[2], inst[3]);
		}
		if (commandName.equals("atni")) {
			command = new Command_ATID(sensorNode, inst[1]);
		}
		if (commandName.equals("atid")) {
			command = new Command_ATNID(sensorNode, inst[1]);
		}
		if (commandName.equals("atch")) {
			command = new Command_ATCH(sensorNode, inst[1]);
		}
		if (commandName.equals("atmy")) {
			command = new Command_ATMY(sensorNode, inst[1]);
		}
		if (commandName.equals("atpl")) {
			command = new Command_ATPL(sensorNode, inst[1]);
		}
		if (commandName.equals("while")) {
			endof.push("endwhile");
			Command_WHILE commandWhile = new Command_WHILE(sensorNode, instStr);
			if (script.getCurrentWhile() != null){
				commandWhile.setParent(script.getCurrentWhile());
			}
			script.add(commandWhile);
			script.setCurrentWhile(commandWhile);
		}		
		if (commandName.equals("endwhile")) {
			Command_ENDWHILE commandWEndhile = new Command_ENDWHILE(sensorNode);
			commandWEndhile.setCurrentWhile(script.getCurrentWhile());
			//----
			script.getCurrentWhile().setEndWhileIndex(script.size());
			//----
			script.removeCurrentWhile();
			script.add(commandWEndhile);			
		}
		
		if (commandName.equals("for")) {
			endof.push("endfor");
			Command_FOR cmdFor = null;
			if(inst.length==4) cmdFor = new Command_FOR(sensorNode, inst[1], inst[2], inst[3], "1");
			if(inst.length==5) cmdFor = new Command_FOR(sensorNode, inst[1], inst[2], inst[3], inst[4]);
			if (script.getCurrentFor() != null){
				cmdFor.setParent(script.getCurrentFor());
			}
			script.add(cmdFor);
			script.setCurrentFor(cmdFor);
		}
		if (commandName.equals("endfor")) {
			Command_ENDFOR cmdEndFor = new Command_ENDFOR(sensorNode);
			cmdEndFor.setCurrentFor(script.getCurrentFor());
			
			//----
			script.getCurrentFor().setEndForIndex(script.size());
			//----
			
			script.removeCurrentFor();
			script.add(cmdEndFor);
		}
		
		if (commandName.equals("if")) {
			endof.push("endif");
			Command_IF commandIf = new Command_IF(sensorNode, instStr);
			if (script.getCurrentIf() != null){
				commandIf.setParent(script.getCurrentIf());
			}			
			script.setCurrentIf(commandIf);
		}
		
		if (commandName.equals("else")) {
			command = new Command_ELSE(sensorNode);
			script.getCurrentIf().setElseIndex(script.size());
		}		
		
		if (commandName.equals("endif")) {
			command = new Command_ENDIF(sensorNode);
			script.getCurrentIf().setEndIfIndex(script.size());
			script.removeCurrentIf();
		}
		
		if (commandName.equals("goto")) {
			command = new Command_GOTO(sensorNode, inst[1]);
		}
		
		if (commandName.equals("rotate")) {
			command = new Command_ROTATE(sensorNode, inst[1], inst[2]);
		}
		
		if (commandName.equals("move")) {
			command = new Command_MOVE(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		
		if (commandName.equals("rmove")) {
			command = new Command_RMOVE(sensorNode, inst[1]);
		}
		
		if (commandName.equals("cdistance")) {
			command = new Command_CDISTANCE(sensorNode, inst[1], inst[2], inst[3], inst[4], inst[5]);
		}
		
		if (commandName.equals("distance")) {
			command = new Command_DISTANCE(sensorNode, inst[1], inst[2]);
		}
		
		if (commandName.equals("drssi")) {
			command = new Command_DRSSI(sensorNode, inst[1]);
		}
		
		if (commandName.equals("inc")) {
			command = new Command_PLUS(sensorNode, inst[1], "$"+inst[1], "1");
		}
		
		if (commandName.equals("dec")) {
			command = new Command_MINUS(sensorNode, inst[1], "$"+inst[1], "1");
		}
		
		if (commandName.equals("int")) {
			command = new Command_INT(sensorNode, inst[1], inst[2]);
		}
		
		if (commandName.equals("conc")) {
			command = new Command_CONC(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		
		if (commandName.equals("deconc")) {
			command = new Command_DECONC(sensorNode, inst[1], inst[2], inst[3], inst[4]);
		}
		
		if (commandName.equals("getinfo")) {
			command = new Command_GETINFO(sensorNode, inst[1]);
		}
		
		if (commandName.equals("time")) {
			command = new Command_TIME(sensorNode, inst[1]);
		}
		
		if (commandName.equals("cprint")) {
			command = new Command_CPRINT(sensorNode, inst);
		}
		
		//-------
		// This part must be here (at the end). All new commands must be added before (above)
		
		if (command != null) {
			script.add(command);
			command.setCurrentIf(script.getCurrentIf());
			command.setCurrentWhile(script.getCurrentWhile());
			command.setCurrentFor(script.getCurrentFor());
		}
		
		
	}
	
}
