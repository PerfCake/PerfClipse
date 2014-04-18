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
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.window.Window;
import org.perfclipse.core.commands.AddPeriodCommand;
import org.perfclipse.core.commands.DeleteDestinationCommand;
import org.perfclipse.core.commands.EditDestinationEnabledCommand;
import org.perfclipse.core.model.DestinationModel;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.wizards.DestinationEditWizard;
import org.perfclipse.ui.wizards.PeriodAddWizard;

/**
 * @author Jakub Knetl
 *
 */
public class DestionationEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private ReporterModel reporter;
	private DestinationModel destination;

	public DestionationEditPolicy(ReporterModel reporter,
			DestinationModel destination) {
		super();
		this.reporter = reporter;
		this.destination = destination;
	}

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteDestinationCommand(reporter, destination);
	}

	@Override
	protected Command createPropertiesCommand(Request request) {
		DestinationEditWizard wizard = new DestinationEditWizard(destination);
		if (Utils.showWizardDialog(wizard) == Window.OK){
			CompoundCommand command = wizard.getCommand();
			if (!command.isEmpty()){
				return command;
			}
		}
		return null;
	}

	@Override
	protected Command createAddPeriodCommand(Request request) {
		PeriodAddWizard wizard = new PeriodAddWizard();
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return null;
		
		return new AddPeriodCommand(destination, wizard.getPeriod());
	}

	@Override
	protected Command createSwitchCommand(Request request) {
		boolean enable = !destination.isEnabled();
		return new EditDestinationEnabledCommand(destination, enable);
	}
	
	
	
	
	
}
