package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.ui.gef.commands.DeleteMessageCommand;

public class DeleteMessageEditPolicy extends ComponentEditPolicy {

	MessagesModel messages;
	MessageModel message;

	
	
	public DeleteMessageEditPolicy(MessagesModel messages, MessageModel message) {
		super();
		this.messages = messages;
		this.message = message;
	}

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteMessageCommand(messages, message);
	}
	
}
