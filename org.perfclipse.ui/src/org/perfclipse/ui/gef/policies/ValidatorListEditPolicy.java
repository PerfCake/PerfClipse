package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.commands.AddValidatorCommand;
import org.perfclipse.ui.gef.commands.MoveValidatorCommand;

public class ValidatorListEditPolicy extends AbstractListEditPolicy {

	private ValidationModel model;
	
	public ValidatorListEditPolicy(ValidationModel model) {
		super();
		this.model = model;
	}

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		int newIndex = calculateNewIndex(child, after);
		if (newIndex < 0)
			return null;
		
		return new MoveValidatorCommand(model, newIndex, (ValidatorModel) child.getModel());
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object type = request.getNewObjectType();
		if (type == Validator.class){
			Validator validator = (Validator) request.getNewObject();
			return new AddValidatorCommand(validator, model);
		}
		return null;
	}

}
