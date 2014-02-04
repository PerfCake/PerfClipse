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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class ScenarioWizard extends Wizard implements INewWizard {

	private IWorkbench workbench;
	private ScenarioFirstPage firstPage;
	
	private List<WizardPage> pages = new ArrayList<>();

	public ScenarioWizard() {
		super();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;

	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void addPages(){
		pages.add(new ScenarioFirstPage());

		for(WizardPage p: pages){
			addPage(p);
		}
	}

}
