package org.perfclipse.core.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.core.model.ValidationModel;

public class EditValidationEnabledCommand extends Command {

	private ValidationModel validation;
	private boolean oldValue;
	private boolean newValue;

	public EditValidationEnabledCommand(ValidationModel validation, boolean newValue) {
		super("Edit validation enabled flag");
		this.validation = validation;
		this.newValue = newValue;
		this.oldValue = validation.getValidation().isEnabled();
	}

	@Override
	public void execute() {
		validation.setEnabled(newValue);
	}

	@Override
	public void undo() {
		validation.setEnabled(oldValue);
	}
}
