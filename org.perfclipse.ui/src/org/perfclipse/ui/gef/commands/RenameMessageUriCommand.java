package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.model.MessageModel;

public class RenameMessageUriCommand extends Command {

	private String newUri;
	private String oldUri;
	private MessageModel messageModel;

	public RenameMessageUriCommand(MessageModel message, String newUri) {
		super("rename message");
		this.messageModel = message;
		this.oldUri = message.getMessage().getUri();
		this.newUri = newUri;
	}
	
	@Override
	public void execute() {
		messageModel.setUri(newUri);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		messageModel.setUri(oldUri);
	}

}
