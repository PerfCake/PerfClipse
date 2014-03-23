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
import org.perfcake.model.Header;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.ui.gef.commands.AddHeaderCommand;
import org.perfclipse.ui.wizards.HeaderAddWizard;

/**
 * @author Jakub Knetl
 *
 */
public class AddHeaderSelectionAdapter extends AbstractCommandSelectionAdapter {

	private TableViewer viewer;
	private MessageModel message;
	private ModelMapper mapper;
	private Header header;

	/**
	 * @param commands
	 * @param viewer
	 * @param message
	 */
	public AddHeaderSelectionAdapter(List<Command> commands,
			TableViewer viewer, MessageModel message) {
		super(commands);
		this.viewer = viewer;
		this.message = message;
		if (message != null){
			this.mapper = message.getMapper();
		}
		else{
			this.mapper = new ModelMapper();
		}
	}
	
	


	@Override
	public void widgetSelected(SelectionEvent e) {
		HeaderAddWizard wizard = new HeaderAddWizard();
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);

		if (dialog.open() != Window.OK)
			return;

		header = new org.perfcake.model.ObjectFactory().createHeader();
		header.setName(wizard.getName());
		header.setValue(wizard.getValue());
		
		viewer.add(mapper.getModelContainer(header));

		super.widgetSelected(e);
	}




	@Override
	protected Command getCommand() {
		if (message!= null && header != null){
			return new AddHeaderCommand(message, header);
		}
		
		return null;
	}

}
