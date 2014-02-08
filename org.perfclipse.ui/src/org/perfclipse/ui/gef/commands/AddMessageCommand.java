package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.ScenarioModel.Messages;
import org.perfclipse.model.ScenarioModel.Messages.Message;

public class AddMessageCommand extends Command {

	ScenarioModel.Messages.Message newMessage;
	ScenarioModel.Messages messages;

	public AddMessageCommand(Message newMessage, Messages messages) {
		super("Add message");
		this.newMessage = newMessage;
		this.messages = messages;
	}

	@Override
	public void execute() {
		messages.addMessage(newMessage);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		messages.removeMessage(newMessage);
	}
	
	
	
	

}
