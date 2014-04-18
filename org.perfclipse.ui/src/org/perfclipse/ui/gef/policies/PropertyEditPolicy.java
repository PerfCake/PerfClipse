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

package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.window.Window;
import org.perfclipse.core.commands.DeletePropertyCommand;
import org.perfclipse.core.model.IPropertyContainer;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.wizards.PropertyEditWizard;
import org.perfclipse.wizards.WizardUtils;

/**
 * @author Jakub Knetl
 *
 */
public class PropertyEditPolicy extends AbstractPerfCakeComponentEditPolicy {
	
	private IPropertyContainer properties;
	private PropertyModel property;

	public PropertyEditPolicy(IPropertyContainer properties,
			PropertyModel property) {
		super();
		this.properties = properties;
		this.property = property;
	}

	@Override
	protected Command createDeleteCommand(GroupRequest request) {
		return new DeletePropertyCommand(properties, property);
	}

	@Override
	protected Command createPropertiesCommand(Request request) {
		PropertyEditWizard wizard = new PropertyEditWizard(property);
		if (WizardUtils.showWizardDialog(wizard) == Window.OK){
			if (!wizard.getCommand().isEmpty())
				return wizard.getCommand();
		}
		return null;
	}
	
	
}
