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

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.perfcake.model.Scenario;
import org.perfclipse.core.commands.AddDestinationCommand;
import org.perfclipse.core.model.DestinationModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ScenarioModel;
import org.perfclipse.wizards.DestinationAddWizard;
import org.perfclipse.wizards.WizardUtils;

/**
 * @author Jakub Knetl
 *
 */
public class AddDestinationSelectionAdapter extends
		AbstractCommandSelectionAdapter {

	private ReporterModel reporter;
	private DestinationModel destination;
	private ModelMapper mapper;

	
	/**
	 * @param commands
	 * @param viewer
	 * @param reporter
	 */
	public AddDestinationSelectionAdapter(List<Command> commands,
			TableViewer viewer, ReporterModel reporter) {
		super(commands, viewer);
		this.reporter = reporter;
		if (reporter == null)
			mapper = new ModelMapper(new ScenarioModel(new Scenario()));
		else
			mapper = reporter.getMapper();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		DestinationAddWizard wizard = new DestinationAddWizard();
		
		if (WizardUtils.showWizardDialog(wizard) != Window.OK)
			return;
		
		destination = (DestinationModel) mapper.getModelContainer(wizard.getDestination());
		getViewer().add(destination);

		super.widgetSelected(e);
	}



	@Override
	protected Command getCommand() {
		if (reporter != null && destination != null){
			return new AddDestinationCommand(destination.getDestination(), reporter);
		}
		return null;
	}

}
