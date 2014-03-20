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

import java.util.List;

import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ReportingModel;

/**
 * @author Jakub Knetl
 *
 */
public class ReportingEditWizard extends AbstractPerfCakeEditWizard {

	private ReportingModel reporting;
	private List<ReporterModel> reporters;
	private ReportingPage reportingPage;

	/**
	 * @param commandLabel
	 */
	public ReportingEditWizard(ReportingModel reporting, List<ReporterModel> reporters) {
		super("Edit reporter");
		this.reporting = reporting;
		this.reporters = reporters;
	}

	@Override
	public void addPages() {
		reportingPage = new ReportingPage(reporting, reporters);
		addPage(reportingPage);

		super.addPages();
	}

	
}
