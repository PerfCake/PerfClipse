package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;

public class DeleteMessageCommand extends Command {

	private MessagesModel messages;
	private MessageModel message;
	private int index;

	public DeleteMessageCommand(MessagesModel messages,
			MessageModel message)
	{
		super("Delete message");
		this.messages = messages;
		this.message = message;
	}

	@Override
	public void execute() {
		index = messages.getMessages().getMessage().indexOf(message.getMessage());
		messages.removeMessage(message.getMessage());
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		messages.addMessage(index, message.getMessage());
	}
	
	
}
