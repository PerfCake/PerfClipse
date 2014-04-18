/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2013 Jakub Knetl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.perfclipse.core.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.core.model.ValidationModel;
import org.perfclipse.core.model.ValidatorModel;

/**
 * @author Jakub Knetl
 *
 */
public class MoveValidatorCommand extends Command {

	private ValidationModel validation;
	private int newIndex;
	private int oldIndex;
	private ValidatorModel validator;
	/**
	 * @param validation
	 * @param newIndex
	 * @param validator
	 */
	public MoveValidatorCommand(ValidationModel validation, int newIndex,
			ValidatorModel validator) {
		super("Move validator");
		this.validation = validation;
		this.newIndex = newIndex;
		this.validator = validator;
		oldIndex = validation.getValidation().getValidator().indexOf(validator.getValidator());
	}
	@Override
	public void execute() {
		validation.removeValidator(validator.getValidator());
		validation.addValidator(newIndex, validator.getValidator());
	}
	@Override
	public void undo() {
		validation.removeValidator(validator.getValidator());
		validation.addValidator(oldIndex, validator.getValidator());
	}
	
	
	
	
}
