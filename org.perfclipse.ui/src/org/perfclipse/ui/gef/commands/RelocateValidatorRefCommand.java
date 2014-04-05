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
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ValidatorRefModel;

/**
 * @author Jakub Knetl
 *
 */
public class RelocateValidatorRefCommand extends Command {

	private MessageModel oldMessage;
	private MessageModel newMessage;
	private ValidatorRefModel oldRef;
	private ValidatorRefModel newRef;
	/**
	 * @param newMessage
	 * @param ref
	 */
	public RelocateValidatorRefCommand(MessageModel newMessage,
			MessageModel oldMessage, ValidatorRefModel ref) {
		super("Move validatorRef");
		this.newMessage = newMessage;
		this.oldMessage = oldMessage;
		this.oldRef = ref;
		ValidatorRef newReference = new ObjectFactory().createScenarioMessagesMessageValidatorRef();
		newReference.setId(oldRef.getValidatorRef().getId());
		this.newRef = (ValidatorRefModel) oldMessage.getMapper().getModelContainer(newReference);
	}
	@Override
	public void execute() {
		oldMessage.removeValidatorRef(oldRef.getValidatorRef());
		newMessage.addValidatorRef(newRef.getValidatorRef());
	}
	@Override
	public void undo() {
		newMessage.removeValidatorRef(newRef.getValidatorRef());
		oldMessage.addValidatorRef(oldRef.getValidatorRef());
	}
}
