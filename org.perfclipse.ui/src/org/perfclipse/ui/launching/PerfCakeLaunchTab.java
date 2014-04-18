/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
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

package org.perfclipse.ui.launching;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.ui.Activator;
import org.perfclipse.ui.PerfClipseConstants;

public class PerfCakeLaunchTab extends AbstractLaunchConfigurationTab {
	
	public static final String TAB_NAME = "PerfCake configuration";
	
	static final Logger log = Activator.getDefault().getLogger();

	Composite container;
	GridLayout layout;

	Label projectLabel; 
	Text projectText;
	Button projectBrowseButton;
	
	Label scenarioLabel;
	Text scenarioText;
	Button scenarioBrowseButton;
	
	Label propertyLabel;
	Text propertyText;

	@Override
	public void createControl(final Composite parent) {
		container = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		container.setLayout(layout);
		
		projectLabel = new Label(container, SWT.NONE);
		projectLabel.setText("Project: ");
		
		projectText = new Text(container, SWT.NONE);
		projectText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		projectText.setEditable(false);
		
		projectBrowseButton = new Button(container, SWT.NONE);
		projectBrowseButton.setText("Browse");
		projectBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(parent.getShell(), new WorkbenchLabelProvider(),
						new ProjectContentProvider());
				dialog.setTitle("Select project");
				dialog.setMessage("Select the project with scenarios");
				dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
				if (dialog.open() == Window.OK)
					projectText.setText(((IResource) dialog.getFirstResult()).getName());
				updateLaunchConfigurationDialog();
			}
		});
		
		scenarioLabel = new Label(container, SWT.NONE);
		scenarioLabel.setText("Scenario file: ");
		
		scenarioText = new Text(container, SWT.NONE);
		scenarioText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scenarioText.setEditable(false);
		
		scenarioBrowseButton = new Button(container, SWT.NONE);
		scenarioBrowseButton.setText("Browse");
		scenarioBrowseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				//TODO: get input from project input text and open directory with project (or scenario subdirectory)
				Object dialogInput = ResourcesPlugin.getWorkspace().getRoot();
				if (! "".equals(projectText.getText())){
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectText.getText());
					if (project.exists()){
						IFolder scenarioFolder = project.getFolder(PerfClipseConstants.SCENARIO_DIR_NAME);
						if (scenarioFolder.exists()){
							dialogInput = scenarioFolder; 
						}
						else{
							dialogInput = project;
						}

					}
				}
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(parent.getShell(), new WorkbenchLabelProvider(),
						new XMLFileContentProvider());
				dialog.setTitle("Select XML file with scenario definition");
				dialog.setMessage("Select the scenario file to execute:");
				dialog.setInput(dialogInput);
				if (dialog.open() == Window.OK)
					scenarioText.setText(((IFile) dialog.getFirstResult()).getProjectRelativePath().toPortableString());
				updateLaunchConfigurationDialog();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		
		propertyLabel = new Label(container, SWT.NONE);
		propertyLabel.setText("PerfCake system properties: ");
		
		propertyText = new Text(container, SWT.MULTI);
		propertyText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
				
			}
		});
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		propertyText.setLayoutData(data);

		
		setControl(container);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PerfCakeLaunchConstants.PROJECT,	"");
		configuration.setAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, "");
		configuration.setAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES, "");

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		projectText.setText("");
		scenarioText.setText("");
		propertyText.setText("");
		
		
		try{
			projectText.setText(configuration.getAttribute(PerfCakeLaunchConstants.PROJECT, ""));
			scenarioText.setText(configuration.getAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, ""));
			propertyText.setText(configuration.getAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES, ""));

		} catch (CoreException e){
			log.error("Cannot obtain attributes from launch configuration", e);
			MessageDialog.openError(getShell(), "Cannot obtain configureation", "Launch configuration cannot be loaded from stored attributes.");
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PerfCakeLaunchConstants.PROJECT, projectText.getText());
		configuration.setAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, scenarioText.getText());
		configuration.setAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES, propertyText.getText());
	}
	
	 @Override
	    public boolean isValid(final ILaunchConfiguration launchConfig) {
	        try {
	            String projectName = launchConfig.getAttribute(PerfCakeLaunchConstants.PROJECT, "");
	            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	            if (project.exists()) {
	                String fileName = launchConfig.getAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, "");
	                IFile file = project.getFile(fileName);
	                return file.exists();
	            }

	        } catch (Exception e) {
	            // on any configuration error
	            setErrorMessage("Invalid text file selected.");
	        }

	        return false;
	    }
	 
	 

	@Override
	public boolean canSave() {
		return (!scenarioText.getText().isEmpty() && ! scenarioText.getText().isEmpty()); 
	}

	@Override
	public String getName() {
		return TAB_NAME;
	}

}
