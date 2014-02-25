package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.commands.AddMessageCommand;

public class MessagesListEditPolicy extends OrderedLayoutEditPolicy {

	private MessagesModel model;
	private ScenarioModel parent;

	public MessagesListEditPolicy(MessagesModel model, ScenarioModel parent) {
		this.model = model;
		this.parent = parent;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object type = request.getNewObjectType();
		if (type == Message.class){
			Message message = (Scenario.Messages.Message) request.getNewObject();
			if (model.getMessages() == null){
				model.createMessages();
				parent.setMessages(model.getMessages());
			}
			return new AddMessageCommand(message, model);
		}
		return null;
	}
	
	@Override
	protected EditPart getInsertionReference(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

}
