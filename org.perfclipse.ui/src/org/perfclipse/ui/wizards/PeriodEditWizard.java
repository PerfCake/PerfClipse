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

import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfclipse.model.PeriodModel;
import org.perfclipse.ui.gef.commands.EditPeriodType;
import org.perfclipse.ui.gef.commands.EditPeriodValue;
import org.perfclipse.ui.wizards.pages.PeriodPage;

/**
 * @author Jakub Knetl
 *
 */
public class PeriodEditWizard extends AbstractPerfCakeEditWizard {

	private PeriodModel period;
	private PeriodPage periodPage;
	/**
	 * @param period
	 */
	public PeriodEditWizard(PeriodModel period) {
		super("Edit period");
		this.period = period;
	}
	@Override
	public boolean performFinish() {
		Period p = period.getPeriod();
	
		if (p.getType() == null ||
				!p.getType().equals(periodPage.getPeriodName()))
			getCommand().add(new EditPeriodType(period, periodPage.getPeriodName()));
		if (p.getValue() == null ||
				!p.getValue().equals(periodPage.getPeriodValue()))
			getCommand().add(new EditPeriodValue(period, periodPage.getPeriodValue()));
		
		return super.performFinish();
	}
	@Override
	public void addPages() {
		periodPage = new PeriodPage(period);
		addPage(periodPage);
		super.addPages();
	}
}
