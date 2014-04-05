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

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Property;
import org.perfclipse.model.IPropertyContainer;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.commands.AddPropertyCommand;
import org.perfclipse.ui.gef.commands.MovePropertyCommand;
import org.perfclipse.ui.wizards.PropertyAddWizard;

/**
 * @author Jakub Knetl
 *
 */
public class PropertyListEditPolicy extends AbstractListEditPolicy implements
		EditPolicy {

	IPropertyContainer properties;

	public PropertyListEditPolicy(IPropertyContainer propertiesModel) {
		this.properties = propertiesModel;
	}

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		List<?> list = getHost().getChildren();
		int newIndex = list.indexOf(after);

		if (newIndex < 0 || newIndex == list.indexOf(child))
			return null;
		
		return new MovePropertyCommand(properties, newIndex, (PropertyModel) child.getModel());

	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request.getNewObjectType() == Property.class){
		
			PropertyAddWizard wizard = new PropertyAddWizard();
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			WizardDialog dialog = new WizardDialog(shell, wizard);
			
			if (dialog.open() != Window.OK)
				return null;
				
			Property newProperty = (Property) request.getNewObject();
			
			newProperty.setName(wizard.getName());
			newProperty.setValue(wizard.getValue());
			return new AddPropertyCommand(newProperty, properties);
		}
		return null;
	}
	

}
