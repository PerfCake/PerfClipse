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
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.model.PeriodModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.wizards.pages.DestinationPage;

/**
 * @author Jakub Knetl
 *
 */
public class DestinationAddWizard extends AbstractPerfCakeAddWizard {

	private DestinationPage destinationPage;
	private Destination destination;

	@Override
	public boolean performFinish() {
		destination = new ObjectFactory().createScenarioReportingReporterDestination();
		destination.setClazz(destinationPage.getDestinationType());
		destination.setEnabled(destinationPage.getEnabled());
		
		for (TableItem i : destinationPage.getPeriodViewer().getTable().getItems()){
			if (i.getData() instanceof PeriodModel){
				PeriodModel p = (PeriodModel) i.getData();
				destination.getPeriod().add(p.getPeriod());
			}
		}
		
		for (TableItem i : destinationPage.getPropertyViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				destination.getProperty().add(p.getProperty());
			}
		}

		return true;
	}

	@Override
	public void addPages() {
		destinationPage = new DestinationPage();
		addPage(destinationPage);

		super.addPages();
	}

	public Destination getDestination() {
		return destination;
	}
	
}
