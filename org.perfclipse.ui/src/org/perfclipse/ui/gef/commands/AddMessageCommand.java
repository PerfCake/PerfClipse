package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfcake.model.Scenario;
import org.perfclipse.model.MessagesModel;

public class AddMessageCommand extends Command {

	Scenario.Messages.Message newMessage;
	MessagesModel messages;

	public AddMessageCommand(Scenario.Messages.Message newMessage, MessagesModel messages) {
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
