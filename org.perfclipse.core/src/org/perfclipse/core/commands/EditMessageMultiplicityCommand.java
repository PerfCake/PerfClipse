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
import org.perfclipse.core.model.MessageModel;

/**
 * @author Jakub Knetl
 *
 */
public class EditMessageMultiplicityCommand extends Command {

	private MessageModel message;
	private String newValue;
	private String oldValue;

	public EditMessageMultiplicityCommand(MessageModel message, String newValue) {
		super("Edit multiplicity");
		this.message = message;
		this.newValue = newValue;
		this.oldValue = message.getMessage().getMultiplicity();
	}

	@Override
	public void execute() {
		message.setMultiplicity(newValue);
	}

	@Override
	public void undo() {
		message.setMultiplicity(oldValue);
	}
}
