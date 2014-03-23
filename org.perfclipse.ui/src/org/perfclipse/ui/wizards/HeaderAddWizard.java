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

import org.eclipse.jface.wizard.IWizardPage;

/**
 * @author Jakub Knetl
 *
 */
public class HeaderAddWizard extends AbstractPerfCakeAddWizard {

	private HeaderPage headerPage;
	private String name;
	private String value;

	@Override
	public boolean performFinish() {
		name = headerPage.getNameText().getText();
		value = headerPage.getValueText().getText();

		return true;
	}

	@Override
	public void addPage(IWizardPage page) {
		headerPage = new HeaderPage();
		addPage(headerPage);
		super.addPage(page);
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
