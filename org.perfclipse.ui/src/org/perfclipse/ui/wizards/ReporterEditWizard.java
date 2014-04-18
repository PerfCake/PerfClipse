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

package org.perfclipse.ui.wizards;

import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.ui.gef.commands.EditReporterEnabledCommand;
import org.perfclipse.ui.gef.commands.EditReporterTypeCommand;
import org.perfclipse.ui.wizards.pages.ReporterPage;

/**
 * @author Jakub Knetl
 *
 */
public class ReporterEditWizard extends AbstractPerfCakeEditWizard {

	private ReporterModel reporter;
	private ReporterPage reporterPage;

	/**
	 * @param reporter
	 */
	public ReporterEditWizard(ReporterModel reporter) {
		super("Edit reporter");
		this.reporter = reporter;
	}

	@Override
	public boolean performFinish() {
		Reporter r = reporter.getReporter();
		if (r.getClazz() == null ||
				!r.getClazz().equals(reporterPage.getReporterType())){
			getCommand().add(new EditReporterTypeCommand(reporter, reporterPage.getReporterType()));
		}
		if (r.isEnabled() != reporterPage.getEnabled()){
			getCommand().add(new EditReporterEnabledCommand(reporter, reporterPage.getEnabled()));
		}
		return super.performFinish();
	}

	@Override
	public void addPages() {
		reporterPage = new ReporterPage(reporter);
		addPage(reporterPage);
		super.addPages();
	}
	
	
	
	

}
