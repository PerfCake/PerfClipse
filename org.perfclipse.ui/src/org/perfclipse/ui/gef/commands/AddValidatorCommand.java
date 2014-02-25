package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.ValidationModel;

public class AddValidatorCommand extends Command {

	Validator newValidator;
	ValidationModel validation;

	public AddValidatorCommand(Validator newValidator,
			ValidationModel validation) {
		super("Add validator");
		this.newValidator = newValidator;
		this.validation = validation;
	}

	@Override
	public void execute() {
		validation.addValidator(newValidator);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		validation.removeValidator(newValidator);
	}
	
	
	
	
}
