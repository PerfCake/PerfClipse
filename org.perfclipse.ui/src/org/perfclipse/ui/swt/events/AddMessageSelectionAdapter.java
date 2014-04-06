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

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.perfcake.model.Scenario;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.AddMessageCommand;
import org.perfclipse.ui.wizards.MessageAddWizard;

/**
 * @author Jakub Knetl
 *
 */
public class AddMessageSelectionAdapter extends AbstractCommandSelectionAdapter {

	private MessagesModel messages;
	private MessageModel message;
	private ModelMapper mapper;
	
	
	//List of validators which will be created invoked by Add message wizard.
	private List<ValidatorModel> validators;
	
	//scenario file
	private IFile scenarioFile;
	
	/**
	 * @param commands
	 * @param viewer
	 * @param messages
	 */
	public AddMessageSelectionAdapter(List<Command> commands,
			TableViewer viewer, MessagesModel messages, IFile scenarioFile) {
		super(commands, viewer);
		this.messages = messages;
		this.scenarioFile = scenarioFile;
		if (messages != null)
			mapper = messages.getMapper();
		else
			//TODO: dummy sceanrio since it is not needed
			this.mapper = new ModelMapper(new ScenarioModel(new Scenario()));
	}

	

	@Override
	public void widgetSelected(SelectionEvent e) {
		MessageAddWizard wizard = new MessageAddWizard(scenarioFile);
		wizard.setValidators(validators);
		//TODO: undo editing support Commands?
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return;
		
		
		message = (MessageModel) mapper.getModelContainer(wizard.getMessage());
		getViewer().add(message);
		super.widgetSelected(e);
	}



	@Override
	protected Command getCommand() {
		if (messages != null && message != null){
			return new AddMessageCommand(message.getMessage(), messages);
		}
		return null;
	}

	/**
	 * Initializes list of validators to which new validators (created by wizard) will be added.
	 * These validator are not currently in scenario, so it will be stored in this list.
	 * @param validators non null list of validators (may be empty)
	 */
	public void setValidators(List<ValidatorModel> validators) {
		this.validators = validators;
	}
}
