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

package org.perfclipse.gef.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.window.Window;
import org.perfclipse.core.commands.AddDestinationCommand;
import org.perfclipse.core.commands.DeleteReporterCommand;
import org.perfclipse.core.commands.EditReporterEnabledCommand;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ReportingModel;
import org.perfclipse.wizards.DestinationAddWizard;
import org.perfclipse.wizards.ReporterEditWizard;
import org.perfclipse.wizards.WizardUtils;

/**
 * @author Jakub Knetl
 *
 */
public class ReporterEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private ReportingModel reporting;
	private ReporterModel reporter;

	public ReporterEditPolicy(ReportingModel reporting,
			ReporterModel reporter) {
		super();
		this.reporting = reporting;
		this.reporter = reporter;
	}

	@Override
	protected Command createDeleteCommand(GroupRequest request) {
		return new DeleteReporterCommand(reporting, reporter);
	}

	@Override
	protected Command createPropertiesCommand(Request request) {
		ReporterEditWizard wizard = new ReporterEditWizard(reporter);
		if (WizardUtils.showWizardDialog(wizard) == Window.OK){
			if (!wizard.getCommand().isEmpty())
				return wizard.getCommand();
		}
		return null;
	}

	@Override
	protected Command createAddDestinationCommand(Request request) {
		DestinationAddWizard wizard = new DestinationAddWizard();
		if (WizardUtils.showWizardDialog(wizard) != Window.OK)
			return null;
		
		return new AddDestinationCommand(wizard.getDestination(), reporter);
	}

	@Override
	protected Command createSwitchCommand(Request request) {
		boolean enabled = !reporter.isEnabled();
		return new EditReporterEnabledCommand(reporter, enabled);
	}
}
