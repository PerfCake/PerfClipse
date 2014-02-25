package org.perfclipse.ui.gef.policies.directedit;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.commands.RenameValidatorCommand;
import org.perfclipse.ui.gef.figures.ILabeledFigure;

public class ValidatorDirectEditPolicy extends LabelDirectEditPolicy {

	protected ValidatorModel model;

	public ValidatorDirectEditPolicy(ValidatorModel model, ILabeledFigure labeledFigure) {
		super(labeledFigure);
		if (model == null)
			throw new IllegalArgumentException("model cannot be null");
		this.model = model;
	}

	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		return new RenameValidatorCommand(model, (String) request.getCellEditor().getValue());
	}

}
