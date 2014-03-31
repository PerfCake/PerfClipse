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
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.window.Window;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.AddDestinationCommand;
import org.perfclipse.ui.gef.commands.DeleteReporterCommand;
import org.perfclipse.ui.wizards.DestinationAddWizard;
import org.perfclipse.ui.wizards.ReporterEditWizard;

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
		if (Utils.showWizardDialog(wizard) == Window.OK){
			if (!wizard.getCommand().isEmpty())
				return wizard.getCommand();
		}
		return null;
	}

	@Override
	protected Command createAddDestinationCommand(Request request) {
		DestinationAddWizard wizard = new DestinationAddWizard();
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return null;
		
		return new AddDestinationCommand(wizard.getDestination(), reporter);
	}
	
	
	
	
}
