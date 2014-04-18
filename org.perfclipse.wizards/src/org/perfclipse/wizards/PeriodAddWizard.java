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

package org.perfclipse.wizards;

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfclipse.wizards.pages.PeriodPage;

/**
 * @author Jakub Knetl
 *
 */
public class PeriodAddWizard extends AbstractPerfCakeAddWizard {

	private PeriodPage page;
	private Period period;


	/**
	 * 
	 */
	public PeriodAddWizard() {
		super();
		setWindowTitle("Add period");
	}

	@Override
	public boolean performFinish() {
		period = new ObjectFactory().createScenarioReportingReporterDestinationPeriod();
		period.setType(page.getPeriodName());
		period.setValue(page.getPeriodValue());

		return true;
	}

	@Override
	public void addPages() {
		page = new PeriodPage();
		addPage(page);
		super.addPages();
	}

	public Period getPeriod() {
		return period;
	}
	
}
