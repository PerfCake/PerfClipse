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
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.ui.gef.parts.AbstractPerfCakeEditPart;
import org.perfclipse.ui.wizards.ReportingEditWizard;

/**
 * @author Jakub Knetl
 *
 */
public class ReportingEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private ReportingModel reporting;

	public ReportingEditPolicy(ReportingModel reporting) {
		super();
		this.reporting = reporting;
	}

	@Override
	protected Command createPropertiesCommand() {
		List<ReporterModel> reporters = new ArrayList<>(reporting.getReporting().getReporter().size());
		ModelMapper mapper = ((AbstractPerfCakeEditPart) getHost()).getMapper();

		for (Reporter r : reporting.getReporting().getReporter()){
			reporters.add((ReporterModel) mapper.getModelContainer(r));
		}
		
		ReportingEditWizard wizard = new ReportingEditWizard(reporting, reporters);
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
