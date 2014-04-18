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
import org.perfclipse.core.commands.AddPeriodCommand;
import org.perfclipse.core.model.DestinationModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.PeriodModel;
import org.perfclipse.core.model.ScenarioModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.wizards.PeriodAddWizard;

/**
 * @author Jakub Knetl
 *
 */
public class AddPeriodSelectionAdapter extends AbstractCommandSelectionAdapter {

	private PeriodModel period;
	private DestinationModel destination;
	private ModelMapper mapper;
	
	
	/**
	 * @param commands
	 * @param viewer
	 * @param destination
	 */
	public AddPeriodSelectionAdapter(List<Command> commands,
			TableViewer viewer, DestinationModel destination) {
		super(commands, viewer);
		this.destination = destination;
		if (destination != null)
			mapper = destination.getMapper();
		else
			mapper = new ModelMapper(new ScenarioModel(new Scenario()));
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		PeriodAddWizard wizard = new PeriodAddWizard();
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return;
		
		period = (PeriodModel) mapper.getModelContainer(wizard.getPeriod());
		getViewer().add(period);
		super.widgetSelected(e);
	}

	@Override
	protected Command getCommand() {
		if (period != null && destination != null){
			return new AddPeriodCommand(destination, period.getPeriod());
		}
		return null;
	}

}
