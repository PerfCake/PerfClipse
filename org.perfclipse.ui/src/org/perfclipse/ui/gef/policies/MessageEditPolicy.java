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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.HeaderModel;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ValidatorRefModel;
import org.perfclipse.ui.gef.commands.DeleteMessageCommand;
import org.perfclipse.ui.wizards.MessageEditWizard;

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
	protected Command createPropertiesCommand() {
		List<PropertyModel> properties = new ArrayList<>(message.getProperty().size());
		List<HeaderModel> headers = new ArrayList<>(message.getMessage().getHeader().size());
		List<ValidatorRefModel> refs = new ArrayList<>(message.getMessage().getValidatorRef().size());
		
		ModelMapper mapper = message.getMapper();
		for (Property p : message.getProperty()){
			properties.add((PropertyModel) mapper.getModelContainer(p));
		}
		
		for (Header h : message.getMessage().getHeader()){
			headers.add((HeaderModel) mapper.getModelContainer(h));
		}
		
		for (ValidatorRef r : message.getMessage().getValidatorRef()){
			refs.add((ValidatorRefModel) mapper.getModelContainer(r));
		}

		MessageEditWizard wizard = new MessageEditWizard(message, properties, headers, refs);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
		if (dialog.getReturnCode() == Window.OK){
			CompoundCommand command = wizard.getCommand();
			if (!command.isEmpty()){
				return command;
			}
		}
		return null;
	}
}
