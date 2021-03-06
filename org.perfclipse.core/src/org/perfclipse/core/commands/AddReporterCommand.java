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

package org.perfclipse.core.commands;

import org.eclipse.gef.commands.Command;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.core.model.ReportingModel;

public class AddReporterCommand extends Command {

	private ReportingModel reporting;
	private Reporter newReporter;

	public AddReporterCommand(Reporter reporter, ReportingModel reporting) {
		super("Add reporter");
		this.newReporter = reporter;
		this.reporting = reporting;
	}
	
	@Override
	public void execute() {
		reporting.addReporter(newReporter);
	}

	@Override
	public void undo() {
		reporting.removeReporter(newReporter);
	}
}
