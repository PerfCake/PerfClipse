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
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.AddValidatorCommand;
import org.perfclipse.ui.wizards.ValidatorAddWizard;

/**
 * @author Jakub Knetl
 *
 */
public class AddValidatorSelectionAdapater extends
		AbstractCommandSelectionAdapter {

	private ValidatorModel validator;
	private ValidationModel validation;
	private ModelMapper mapper;
	
	
	/**
	 * @param commands
	 * @param viewer
	 * @param validation
	 */
	public AddValidatorSelectionAdapater(List<Command> commands,
			TableViewer viewer, ValidationModel validation) {
		super(commands, viewer);
		this.validation = validation;
		if (validation == null){
			mapper = new ModelMapper(new ScenarioModel(new Scenario()));
		} else {
			mapper = validation.getMapper();
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		ValidatorAddWizard wizard = new ValidatorAddWizard();
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return;
		
		validator = (ValidatorModel) mapper.getModelContainer(wizard.getValidator());
		getViewer().add(validator);
		super.widgetSelected(e);
	}

	@Override
	protected Command getCommand() {
		if (validation != null && validator != null){
			return new AddValidatorCommand(validator.getValidator(), validation);
		}
		return null;
	}

}
