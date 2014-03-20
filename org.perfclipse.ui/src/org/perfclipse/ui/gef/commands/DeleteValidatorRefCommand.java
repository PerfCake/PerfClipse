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
import org.perfclipse.model.MessageModel;

/**
 * @author Jakub Knetl
 *
 */
public class DeleteValidatorRefCommand extends Command {

	private MessageModel message;
	private ValidatorRef ref;
	private int index;
	
	public DeleteValidatorRefCommand(MessageModel message, ValidatorRef ref) {
		super("Delete validator reference");
		this.message = message;
		this.ref = ref;
		index = message.getMessage().getValidatorRef().indexOf(ref);
	}

	@Override
	public void execute() {
		message.removeValidatorRef(ref);
	}

	@Override
	public void undo() {
		message.addValidatorRef(index, ref);
	}
}
