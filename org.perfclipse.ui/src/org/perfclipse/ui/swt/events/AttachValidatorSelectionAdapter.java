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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Validation;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorRefModel;
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
	private ValidatorRefModel ref;
	private ModelMapper mapper;

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
		ValidationModel validation = null;
		if (message != null){

			ModelMapper mapper = message.getMapper();
			Validation v = mapper.getScenario().getScenario().getValidation();
			if (v != null)
				validation = (ValidationModel) mapper.getModelContainer(v);
		}

		ValidatorAttachWizard wizard = new ValidatorAttachWizard(validation);
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return;
		
		ref = (ValidatorRefModel) mapper.getModelContainer(wizard.getValidatorRef());
		getViewer().add(ref);
		super.widgetSelected(e);
	}

	@Override
	protected Command getCommand() {
		if (ref != null && message != null)
			return new AddValidatorRefCommand(message, ref.getValidatorRef());
		
		return null;
	}

}
