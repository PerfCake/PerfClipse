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

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.window.Window;
import org.perfclipse.core.commands.AddReporterCommand;
import org.perfclipse.core.model.ReportingModel;
import org.perfclipse.wizards.ReporterAddWizard;
import org.perfclipse.wizards.ReportingEditWizard;
import org.perfclipse.wizards.WizardUtils;

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
	protected Command createPropertiesCommand(Request request) {
		ReportingEditWizard wizard = new ReportingEditWizard(reporting);
		if (WizardUtils.showWizardDialog(wizard) == Window.OK){
			CompoundCommand command = wizard.getCommand();
			if (!command.isEmpty()){
				return command;
			}
		}
		
		return null;
	}

	@Override
	protected Command createAddReporterCommand(Request request) {
		ReporterAddWizard wizard = new ReporterAddWizard();
		if (WizardUtils.showWizardDialog(wizard) != Window.OK)
			return null;
		
		return new AddReporterCommand(wizard.getReporter(), reporting);
	}
	

}
