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

import org.eclipse.swt.widgets.TableItem;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.wizards.pages.ReporterPage;

/**
 * @author Jakub Knetl
 *
 */
public class ReporterAddWizard extends AbstractPerfCakeAddWizard {
	
	private ReporterPage page;
	private Reporter reporter;
	

	public ReporterAddWizard() {
		super();
		setWindowTitle("Add reporter");
	}

	@Override
	public boolean performFinish() {
		reporter = new ObjectFactory().createScenarioReportingReporter();
		
		reporter.setClazz(page.getReporterType());
		reporter.setEnabled(page.getEnabled());
		
		for (TableItem i : page.getPropertyViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				reporter.getProperty().add(p.getProperty());
			}
		}

		return true;
	}

	@Override
	public void addPages() {
		page = new ReporterPage();
		addPage(page);
		super.addPages();
	}

	public Reporter getReporter() {
		return reporter;
	}
	
	
}
