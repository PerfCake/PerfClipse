package org.perfclipse.ui.gef.policies.directedit;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.perfclipse.model.MessageModel;
import org.perfclipse.ui.gef.commands.RenameMessageUriCommand;
import org.perfclipse.ui.gef.figures.ILabeledFigure;

public class MessageDirectEditPolicy extends LabelDirectEditPolicy {

	protected MessageModel model;

	public MessageDirectEditPolicy(MessageModel model, ILabeledFigure labeledFigure) {
		super(labeledFigure);
		if (model == null)
			throw new IllegalArgumentException("Model must not be null");
		
		this.model = model;
	}

	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		return new RenameMessageUriCommand(model, (String) request.getCellEditor().getValue());
	}

}
