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

public class EditValidatorTypeCommand extends Command {

	private String newClazz;
	private String oldClazz;
	private ValidatorModel validator;

	public EditValidatorTypeCommand(ValidatorModel validator, String newClazz) {
		super("rename validator");
		this.validator = validator;
		this.oldClazz = validator.getValidator().getClazz();
		this.newClazz = newClazz;
	}
	
	@Override
	public void execute() {
		validator.setClazz(newClazz);
	}

	@Override
	public void undo() {
		validator.setClazz(oldClazz);
	}

}
