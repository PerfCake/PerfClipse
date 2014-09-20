package org.perfclipse.core.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.core.model.MessageModel;

public class EditMessageContentCommand extends Command {

	private MessageModel message;
	private String newValue;
	private String oldValue;

	public EditMessageContentCommand(MessageModel message, String newValue) {
		super("Edit multiplicity");
		this.message = message;
		this.newValue = newValue;
		this.oldValue = message.getMessage().getContent();
	}

	@Override
	public void execute() {
		message.setContent(newValue);
	}

	@Override
	public void undo() {
		message.setContent(oldValue);
	}
}
