package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.model.ValidatorModel;

public class RenameValidatorCommand extends Command {

	private String newClazz;
	private String oldClazz;
	private ValidatorModel validator;

	public RenameValidatorCommand(ValidatorModel validator, String newClazz) {
		super("rename validator");
		this.validator = validator;
		this.oldClazz = validator.getValidator().getClazz();
		this.newClazz = newClazz;
	}
	
	@Override
	public void execute() {
		validator.setClass(newClazz);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		validator.setClass(oldClazz);
	}

}
