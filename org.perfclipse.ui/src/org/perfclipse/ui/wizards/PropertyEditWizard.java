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

import org.perfcake.model.Property;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.ui.gef.commands.EditPropertyNameCommand;
import org.perfclipse.ui.gef.commands.EditPropertyValueCommand;
import org.perfclipse.ui.wizards.pages.PropertyPage;

/**
 * @author Jakub Knetl
 *
 */
public class PropertyEditWizard extends AbstractPerfCakeEditWizard {

	private PropertyModel property;
	private PropertyPage propertyPage;
	/**
	 * @param property
	 */
	public PropertyEditWizard(PropertyModel property) {
		super("Edit property");
		this.property = property;
	}
	@Override
	public boolean performFinish() {
		Property p = property.getProperty();
		if (p.getName() == null ||
				!p.getName().equals(propertyPage.getNameText().getText()))
			getCommand().add(new EditPropertyNameCommand(property, propertyPage.getNameText().getText()));
		
		if (p.getName() == null ||
				!p.getValue().equals(propertyPage.getValueText().getText()))
			getCommand().add(new EditPropertyValueCommand(property, propertyPage.getValueText().getText()));

		return super.performFinish();
	}
	@Override
	public void addPages() {
		propertyPage = new PropertyPage(property);
		addPage(propertyPage);
		super.addPages();
	}
	
	
	
	
}
