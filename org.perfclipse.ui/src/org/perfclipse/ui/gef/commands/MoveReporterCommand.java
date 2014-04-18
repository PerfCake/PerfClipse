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

package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ReportingModel;

/**
 * @author Jakub Knetl
 *
 */
public class MoveReporterCommand extends Command {

	private ReportingModel reporting;
	private int newIndex;
	private int oldIndex;
	private ReporterModel reporter;
	/**
	 * @param reporting
	 * @param newIndex
	 * @param reporter
	 */
	public MoveReporterCommand(ReportingModel reporting, int newIndex,
			ReporterModel reporter) {
		super("Move reporter");
		this.reporting = reporting;
		this.newIndex = newIndex;
		this.reporter = reporter;
		oldIndex = reporting.getReporting().getReporter().indexOf(reporter.getReporter());
	}
	@Override
	public void execute() {
		reporting.removeReporter(reporter.getReporter());
		reporting.addReporter(newIndex, reporter.getReporter());
	}
	@Override
	public void undo() {
		reporting.removeReporter(reporter.getReporter());
		reporting.addReporter(oldIndex, reporter.getReporter());
	}
	
}
