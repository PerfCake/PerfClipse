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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PerfcakeProjectFirstPage extends WizardPage {
	private Text projectNameText;
	private Composite container;

	public PerfcakeProjectFirstPage() {
		super("New PerfCake Project");
		setTitle("Create Perfcake Project");
		setDescription("Enter project name.");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		Label projectNameLabel = new Label(container, SWT.NONE);
		projectNameLabel.setText("Project name:");
		
		projectNameText = new Text(container, SWT.SINGLE | SWT.BORDER);
		projectNameText.setText("");
		projectNameText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {			
				String name = projectNameText.getText();
				if (!name.isEmpty()){
					if (!projectExists(name)){
						setDescription("Create PerfCake project in default workspace.");
						setPageComplete(true);
				} else{
					setPageComplete(false);
					setDescription("Project with this name already exists in the workspace.");
				}

				} else{
					setPageComplete(false);
					setDescription("Enter project name.");
				}
			}

		});
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		projectNameText.setLayoutData(gridData);
		setControl(container);
		setPageComplete(false);

	}

	public String getProjectName() {
		return projectNameText.getText();
	}

	private boolean projectExists(String name) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = workspaceRoot.getProject(name);
		return project.exists();
	}

}
