package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;

public class AddMessageCommand extends Command {

	MessageModel newMessage;
	MessagesModel messages;

	public AddMessageCommand(MessageModel newMessage, MessagesModel messages) {
		super("Add message");
		this.newMessage = newMessage;
		this.messages = messages;
	}

	@Override
	public void execute() {
		messages.addMessageModel(newMessage);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		messages.removeMessageModel(newMessage);
	}
	
	
	
	

}
