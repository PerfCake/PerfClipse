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

package org.perfclipse.ui.swt.events;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Property;
import org.perfclipse.model.IPropertyContainer;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.commands.AddPropertyCommand;

/**
 * 
 * Selection adapter which handles adding property.
 * @author Jakub Knetl
 *
 */
public class AddPropertySelectionAdapter extends AbstractCommandSelectionAdapter {

	private TableViewer viewer;
	private IPropertyContainer propertyContainer;
	private Property property;

	public AddPropertySelectionAdapter(TableViewer viewer,
			List<Command> commands, IPropertyContainer propertyContainer) {
		super(commands);
		this.viewer = viewer;
		this.propertyContainer = propertyContainer;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		String name = getDialogInput("Add property", "Property name: ", "name");
		String value = getDialogInput("Add property", "Property value: ", "value");

		if (name == null || value == null)
			return;

		property = new org.perfcake.model.ObjectFactory().createProperty();
		property.setName(name);
		property.setValue(value);
		
		//TODO: obtain ModelMapper
		viewer.add(new PropertyModel(property));

		super.widgetSelected(e);
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

	@Override
	protected Command getCommand() {
		if (propertyContainer != null){
			return new AddPropertyCommand(property, propertyContainer);
		}
		
		return null;
	}
	
	

}
