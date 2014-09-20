package org.perfclipse.core.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.core.model.ValidationModel;

public class EditValidationFastForwardCommand extends Command {

	private ValidationModel validation;
	private boolean oldValue;
	private boolean newValue;

	public EditValidationFastForwardCommand(ValidationModel validation, boolean newValue) {
		super("Edit validation enabled flag");
		this.validation = validation;
		this.newValue = newValue;
		this.oldValue = validation.getValidation().isFastForward();
	}

	@Override
	public void execute() {
		validation.setFastForward(newValue);
	}

	@Override
	public void undo() {
		validation.setFastForward(oldValue);
	}
}
