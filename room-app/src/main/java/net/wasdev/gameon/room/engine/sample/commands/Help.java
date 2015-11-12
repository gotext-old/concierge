package net.wasdev.gameon.room.engine.sample.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.wasdev.gameon.room.engine.Room;
import net.wasdev.gameon.room.engine.parser.CommandHandler;
import net.wasdev.gameon.room.engine.parser.CommandTemplate;
import net.wasdev.gameon.room.engine.parser.CommandTemplate.ParseNode;
import net.wasdev.gameon.room.engine.parser.Node.Type;
import net.wasdev.gameon.room.engine.parser.ParsedCommand;

public class Help extends CommandHandler {

	private static final CommandTemplate help = new CommandTemplateBuilder().build(Type.VERB, "Help").build();
	
	private static final Set<CommandTemplate> templates = Collections.unmodifiableSet(new HashSet<CommandTemplate>(Arrays.asList(new CommandTemplate[]{
			help
	})));
	
	
	@Override
	public Set<CommandTemplate> getTemplates() {
		return templates;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public void processCommand(Room room, String execBy, ParsedCommand command) {
		List<String> currentCmds = new ArrayList<String>();
		for(CommandHandler c: room.getCommands()){
			Set<String> verbs = new HashSet<String>();
			if(!c.isHidden()){
				for(CommandTemplate t : c.getTemplates()){
					//first node is the verb
					ParseNode v = t.template.get(0);
					verbs.add(v.data);
				}
			}
			for(String verb : verbs){
				currentCmds.add("/"+verb);
			}
		}
		room.playerEvent(execBy, "The following commands are supported: "+currentCmds,null);
	}

	@Override
	public void processUnknown(Room room, String execBy, String origCmd, String cmdWithoutVerb) {
		room.playerEvent(execBy, "I'm sorry, but I'm not sure how I'm supposed to quit "+cmdWithoutVerb, null);
	}

}