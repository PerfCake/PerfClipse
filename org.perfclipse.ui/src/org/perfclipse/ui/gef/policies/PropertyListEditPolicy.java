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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Property;
import org.perfclipse.model.PropertiesModel;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.commands.AddPropertyCommand;

/**
 * @author Jakub Knetl
 *
 */
public class PropertyListEditPolicy extends AbstractListEditPolicy implements
		EditPolicy {

	PropertiesModel properties;
	ScenarioModel scenario;

	public PropertyListEditPolicy(PropertiesModel propertiesModel,
			ScenarioModel scenarioModel) {
		this.properties = propertiesModel;
		this.scenario = scenarioModel;
	}

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EditPart getInsertionReference(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request.getNewObjectType() == Property.class){
			if (properties.getProperties() == null){
				properties.createProperties();
				scenario.setProperties(properties.getProperties());
			}
			Property newProperty = (Property) request.getNewObject();
			String name = getDialogInput("Add property", "Property name: ", "name");
			String value = getDialogInput("Add property", "Property value: ", "value");
			if (name == null || value == null)
				return null;
			
			newProperty.setName(name);
			newProperty.setValue(value);
			return new AddPropertyCommand(newProperty, properties);
		}
		return null;
	}
	
	private String getDialogInput(String title, String label, String initValue) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		//TODO: validator
		InputDialog dialog = new InputDialog(shell, title, label, initValue, null);
		dialog.open();
		if (dialog.getReturnCode() == InputDialog.OK)
			return dialog.getValue();
		
		return null;
	}

}
