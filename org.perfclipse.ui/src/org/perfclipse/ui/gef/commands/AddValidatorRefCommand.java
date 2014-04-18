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
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.core.model.MessageModel;

/**
 * @author Jakub Knetl
 *
 */
public class AddValidatorRefCommand extends Command {

	private MessageModel message;
	private ValidatorRef ref;
	
	public AddValidatorRefCommand(MessageModel message, ValidatorRef ref) {
		super("Add validator reference");
		this.message = message;
		this.ref = ref;
	}

	@Override
	public void execute() {
		message.addValidatorRef(ref);
	}

	@Override
	public void undo() {
		message.removeValidatorRef(ref);
	}

	public MessageModel getMessage() {
		return message;
	}

	public void setMessage(MessageModel message) {
		this.message = message;
	}

	public ValidatorRef getValidatorRef() {
		return ref;
	}

	public void setValidatorRef(ValidatorRef ref) {
		this.ref = ref;
	}
	
}
