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

package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.model.ValidatorModel;

/**
 * @author Jakub Knetl
 *
 */
public class EditValidatorIdCommand extends Command {

	private ValidatorModel validator;
	private String newValue;
	private String oldValue;

	public EditValidatorIdCommand(ValidatorModel validator, String newValue) {
		super("Edit validator id");
		this.validator = validator;
		this.newValue = newValue;
		oldValue = validator.getValidator().getId();
	}

	@Override
	public void execute() {
		validator.setId(newValue);
	}

	@Override
	public void undo() {
		validator.setId(oldValue);
	}
}
