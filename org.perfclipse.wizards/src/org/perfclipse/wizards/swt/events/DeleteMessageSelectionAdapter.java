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

package org.perfclipse.wizards.swt.events;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.TableViewer;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.core.commands.DeleteMessageCommand;
import org.perfclipse.core.commands.DeleteValidatorRefCommand;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.MessagesModel;

/**
 * @author Jakub Knetl
 *
 */
public class DeleteMessageSelectionAdapter extends
		AbstractDeleteCommandSelectionAdapter {

	private MessagesModel messages;
	private MessageModel message;
	
	
	/**
	 * @param commands
	 * @param viewer
	 * @param messages
	 */
	public DeleteMessageSelectionAdapter(List<Command> commands,
			TableViewer viewer, MessagesModel messages, IFile scenarioFile) {
		super(commands, viewer);
		this.messages = messages;
	}

	@Override
	protected Command getCommand() {
		if (message != null && messages != null){
			DeleteMessageCommand deleteMessage = new DeleteMessageCommand(messages, message);
			CompoundCommand command = new CompoundCommand(deleteMessage.getLabel());
			
			if (message.getMessage().getValidatorRef() != null){
				for (ValidatorRef ref : message.getMessage().getValidatorRef()){
					Command c = new DeleteValidatorRefCommand(message, ref);
					command.add(c);
				}
			}
			
			command.add(deleteMessage);
			
			return command;
		}
		return null;
	}

	@Override
	public void handleDeleteData(Object element) {
		message = (MessageModel) element;
	}

	
}
