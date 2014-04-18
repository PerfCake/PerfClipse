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
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ReportingModel;
import org.perfclipse.core.model.ScenarioModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.AddReporterCommand;
import org.perfclipse.ui.wizards.ReporterAddWizard;

/**
 * @author Jakub Knetl
 *
 */
public class AddReporterSelectionAdapter extends AbstractCommandSelectionAdapter {

	private ReportingModel reporting;
	private ReporterModel reporter;
	private ModelMapper mapper;
	/**
	 * @param viewer
	 * @param reporting
	 * @param reporter
	 */
	public AddReporterSelectionAdapter(List<Command> commands,
			TableViewer viewer, ReportingModel reporting) {
		super(commands, viewer);
		this.reporting = reporting;
		if (reporting == null){
			mapper = new ModelMapper(new ScenarioModel(new Scenario()));
		} else{
			mapper = reporting.getMapper();
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		ReporterAddWizard wizard = new ReporterAddWizard();
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return;
		
		reporter = (ReporterModel) mapper.getModelContainer(wizard.getReporter());
		getViewer().add(reporter);
		super.widgetSelected(e);
	}
	
	@Override
	protected Command getCommand() {
		if (reporter != null && reporting != null){
			return new AddReporterCommand(reporter.getReporter(), reporting);
		}
		return null;
	}
	
}
