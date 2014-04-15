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

package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.window.Window;
import org.perfcake.model.Header;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.AddHeaderCommand;
import org.perfclipse.ui.gef.commands.AddValidatorRefCommand;
import org.perfclipse.ui.gef.commands.DeleteMessageCommand;
import org.perfclipse.ui.wizards.HeaderAddWizard;
import org.perfclipse.ui.wizards.MessageEditWizard;
import org.perfclipse.ui.wizards.ValidatorAttachWizard;

public class MessageEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private MessagesModel messages;
	private MessageModel message;

	public MessageEditPolicy(MessagesModel messages, MessageModel message) {
		super();
		this.messages = messages;
		this.message = message;
	}

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteMessageCommand(messages, message);
	}

	@Override
	protected Command createPropertiesCommand(Request request) {
	
		MessageEditWizard wizard = new MessageEditWizard(message);
		if (Utils.showWizardDialog(wizard) == Window.OK){
			CompoundCommand command = wizard.getCommand();
			if (!command.isEmpty()){
				return command;
			}
		}
		return null;
	}

	@Override
	protected Command createAddHeaderCommand(Request request) {
		HeaderAddWizard wizard = new HeaderAddWizard();
		
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return null;
		
		Header h = new ObjectFactory().createHeader();
		h.setName(wizard.getName());
		h.setValue(wizard.getValue());
		
		return new AddHeaderCommand(message, h);
	}

	@Override
	protected Command createAttachValidatorCommand(Request request) {
		if (messages.getMapper().getValidation() == null)
			return null;
		ValidatorAttachWizard wizard = new ValidatorAttachWizard(messages.getMapper().getValidation());
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return null;
		if (wizard.getValidatorRef() == null && wizard.getValidatorRef().isEmpty())
			return null;
		
		CompoundCommand c = new CompoundCommand("Attach validator");
		c.add(wizard.getCommand());
		for (ValidatorRef ref : wizard.getValidatorRef()){
			c.add(new AddValidatorRefCommand(message, ref));
		}
		
		return c;
	}

	
	
}
