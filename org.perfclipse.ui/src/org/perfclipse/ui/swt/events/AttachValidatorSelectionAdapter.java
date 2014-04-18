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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.ScenarioModel;
import org.perfclipse.core.model.ValidationModel;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.core.model.ValidatorRefModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.AddValidatorRefCommand;
import org.perfclipse.ui.wizards.ValidatorAttachWizard;

/**
 * @author Jakub Knetl
 *
 */
public class AttachValidatorSelectionAdapter extends
		AbstractCommandSelectionAdapter {

	private MessageModel message;
	/**
	 * freshly attached validators by this selection adapter
	 */
	private List<ValidatorRefModel> validatorRefs = new ArrayList<>();
	private ModelMapper mapper;
	private ValidatorAttachWizard wizard;
	
	
	private List<ValidatorModel> validators;
	
	public AttachValidatorSelectionAdapter(List<Command> commands,
			TableViewer viewer, MessageModel message) {
		super(commands, viewer);
		this.message = message;
		if (message != null){
			mapper = message.getMapper();
		} else{
			mapper = new ModelMapper(new ScenarioModel(new Scenario()));
		}
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		ValidationModel validation = mapper.getValidation();

		wizard = new ValidatorAttachWizard(validation);
		wizard.setValidators(validators);
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return;
		
		//Execute wizard command (since validator must exist at the time when
		//message adds reference to the validator.
		CompoundCommand command = wizard.getCommand();
		if (!command.isEmpty()){
			command.execute();
		}
		
		if (wizard.getValidatorRef() != null){
			for (ValidatorRef ref : wizard.getValidatorRef()){
				ValidatorRefModel model =  (ValidatorRefModel) mapper.getModelContainer(ref);
				validatorRefs.add(model);
				getViewer().add(model);

			}
		}
		super.widgetSelected(e);
	}

	@Override
	protected Command getCommand() {
		CompoundCommand command = wizard.getCommand();
		/*
		 * Command was executed before but in order to new command can be added
		 * and executed within the context of this command the command have to 
		 * call undo. (It will be executed later on)
		 * */
		command.undo();

		for (ValidatorRefModel ref : validatorRefs){
			if (message != null)
				command.add(new AddValidatorRefCommand(message, ref.getValidatorRef()));
		}

		if (!command.isEmpty())
			return command;
		
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
