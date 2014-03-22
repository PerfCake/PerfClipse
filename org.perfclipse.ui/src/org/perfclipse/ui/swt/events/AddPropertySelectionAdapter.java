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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Property;
import org.perfclipse.model.IPropertyContainer;
import org.perfclipse.ui.gef.commands.AddPropertyCommand;
import org.perfclipse.ui.wizards.PropertyAddWizard;

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

		PropertyAddWizard wizard = new PropertyAddWizard();
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);

		if (dialog.open() != Window.OK)
			return;

		property = new org.perfcake.model.ObjectFactory().createProperty();
		property.setName(wizard.getName());
		property.setValue(wizard.getValue());
		
		viewer.add(propertyContainer.getMapper().getModelContainer(property));

		super.widgetSelected(e);
	}
	
	@Override
	protected Command getCommand() {
		if (propertyContainer != null){
			return new AddPropertyCommand(property, propertyContainer);
		}
		
		return null;
	}
	
	

}
