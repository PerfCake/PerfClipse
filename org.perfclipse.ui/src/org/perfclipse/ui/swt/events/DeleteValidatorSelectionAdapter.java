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

package org.perfclipse.ui.swt.events;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.TableViewer;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.commands.DeleteValidatorCommand;
import org.perfclipse.ui.gef.commands.DeleteValidatorRefCommand;

/**
 * @author Jakub Knetl
 *
 */
public class DeleteValidatorSelectionAdapter extends
		AbstractDeleteCommandSelectionAdapter {

	private ValidatorModel validator;
	private ValidationModel validation;

	private List<MessageModel> messages;
	/**
	 * @param commands
	 * @param viewer
	 * @param validation
	 */
	public DeleteValidatorSelectionAdapter(List<Command> commands,
			TableViewer viewer, ValidationModel validation) {
		super(commands, viewer);
		this.validation = validation;
	}

	@Override
	public void handleDeleteData(Object element) {
		validator = (ValidatorModel) element;

	}

	@Override
	protected Command getCommand() {
		if (validator != null && validation != null){
			Command deleteValidator = new DeleteValidatorCommand(validation, validator);
			CompoundCommand command = new CompoundCommand(deleteValidator.getLabel());
			command.add(deleteValidator);
			if (messages != null){
				for (MessageModel m : messages){
					for (ValidatorRef ref : m.getMessage().getValidatorRef()){
						if (ref.getId().equals(validator.getValidator().getId())){
							command.add(new DeleteValidatorRefCommand(m, ref));
						}
					}
				}
			}
				

			return command;
		}
		return null;
	}

	public List<MessageModel> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageModel> messages) {
		this.messages = messages;
	}
}
