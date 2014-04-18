package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.core.commands.AddValidatorCommand;
import org.perfclipse.core.commands.MoveValidatorCommand;
import org.perfclipse.core.model.ValidationModel;
import org.perfclipse.core.model.ValidatorModel;

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
			
			String id = getValidatorId();
			if (id == null)
				return null;
			
			validator.setId(id);
				
			return new AddValidatorCommand(validator, model);
		}
		return null;
	}

	private String getValidatorId() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IInputValidator inputValidator = new IInputValidator() {
			
			@Override
			public String isValid(String newText) {
				if (newText.isEmpty())
					return "Validator id cannot be empty.";
				
				//if there is no other validator
				if (model.getValidation() == null)
					return null;
				for (Validator v : model.getValidation().getValidator()){
					if (v.getId().equals(newText))
						return "Validator with same ID already exits.";	
				}
				
				return null;
			}
		};
		InputDialog dialog = new InputDialog(shell, "Add validator", "Enter validator ID: ", "", inputValidator);
		dialog.open();
		if (dialog.getReturnCode() == InputDialog.OK)
			return dialog.getValue();
		
		return null;
	}

}
