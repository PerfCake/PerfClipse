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

import org.perfclipse.ui.wizards.pages.PropertyPage;


/**
 * @author Jakub Knetl
 *
 */
public class PropertyAddWizard extends AbstractPerfCakeAddWizard {

	private PropertyPage propertyPage;
	
	private String name;
	private String value;
	
	
	
	/**
	 * 
	 */
	public PropertyAddWizard() {
		super();
		setWindowTitle("Add property");
	}

	@Override
	public boolean performFinish() {
		
		name = propertyPage.getNameText().getText();
		value = propertyPage.getValueText().getText();
		
		return true;
	}

	@Override
	public void addPages() {
		propertyPage = new PropertyPage();
		addPage(propertyPage);
		super.addPages();
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
}
