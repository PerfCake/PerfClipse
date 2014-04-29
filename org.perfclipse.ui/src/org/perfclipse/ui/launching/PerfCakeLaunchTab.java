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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
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
import org.perfclipse.core.PerfClipseConstants;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.ui.Activator;
import org.perfclipse.wizards.PropertyAddWizard;
import org.perfclipse.wizards.swt.events.DoubleClickSelectionAdapter;
import org.perfclipse.wizards.swt.widgets.TableViewerControl;

public class PerfCakeLaunchTab extends AbstractLaunchConfigurationTab {
	
	public static final String TAB_NAME = "PerfCake configuration";
	
	static final Logger log = Activator.getDefault().getLogger();

	private Composite container;
	private GridLayout layout;

	private Label projectLabel; 
	private Text projectText;
	private Button projectBrowseButton;
	
	private Label scenarioLabel;
	private Text scenarioText;
	private Button scenarioBrowseButton;
	
	private Label propertyLabel;
	private TableViewer propertyViewer;
	private TableViewerControl propertyControl;
	
	private List<SystemProperty> properties;


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
		
		propertyViewer = new TableViewer(container);
		propertyViewer.setContentProvider(ArrayContentProvider.getInstance());
		propertyViewer.setInput(properties);
		
		TableViewerColumn nameColumn = new TableViewerColumn(propertyViewer, SWT.NONE);
		nameColumn.getColumn().setWidth(200);
		nameColumn.getColumn().setText("Property name");
		nameColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				if (element instanceof SystemProperty)
					return ((SystemProperty) element).getName();
				return super.getText(element);
			}
			
		});

		
		TableViewerColumn valueColumn = new TableViewerColumn(propertyViewer, SWT.NONE);
		valueColumn.getColumn().setWidth(200);
		valueColumn.getColumn().setText("Property value");
		valueColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				if (element instanceof SystemProperty)
					return ((SystemProperty) element).getValue();
				return super.getText(element);
			}
			
		});
		//TODO: add listener which is ivoked when input changed
//		propertyText.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				updateLaunchConfigurationDialog();
//				
//			}
//		});
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		propertyViewer.getTable().setLayoutData(data);
		
		propertyControl = new TableViewerControl(container, true, SWT.NONE);
		propertyControl.getAddButton().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				PropertyAddWizard wizard = new PropertyAddWizard();
				
				WizardDialog dialog = new WizardDialog(getShell(), wizard);
				
				if (dialog.open() == Window.OK){
					SystemProperty property = new SystemProperty();
					property.setName(wizard.getName());
					property.setValue(wizard.getValue());
					
					properties.add(property);
					propertyViewer.add(property);
					updateLaunchConfigurationDialog();
				}
			}
			
		});
		
		SelectionAdapter editPropertyAdapter = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				PropertyAddWizard wizard = new PropertyAddWizard();
				IStructuredSelection sel = (IStructuredSelection) propertyViewer.getSelection();
				if (sel.size() != 1)
					return;
				
				SystemProperty property = (SystemProperty) sel.getFirstElement();
				
				wizard.setName(property.getName());
				wizard.setValue(property.getValue());
				wizard.updateValues();

				WizardDialog dialog = new WizardDialog(getShell(), wizard);
				
				if (dialog.open() == Window.OK){
					property.setName(wizard.getName());
					property.setValue(wizard.getValue());
					
					propertyViewer.refresh(property);
					updateLaunchConfigurationDialog();
				}
			}
			
		};
		
		propertyViewer.getTable().addMouseListener(new DoubleClickSelectionAdapter(editPropertyAdapter));
		propertyControl.getEditButton().addSelectionListener(editPropertyAdapter);

		propertyControl.getDeleteButton().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) propertyViewer.getSelection();
				if (sel.size() <= 0)
					return;
				
				for (Object selected : sel.toList()){

					SystemProperty property = (SystemProperty) selected;
				
					properties.remove(property);
					propertyViewer.remove(property);
				}
				updateLaunchConfigurationDialog();
			}
			
		});
		
		setControl(container);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PerfCakeLaunchConstants.PROJECT,	"");
		configuration.setAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, "");
		properties = new ArrayList<>();
		configuration.setAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES_NAMES, new ArrayList<String>());
		configuration.setAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES_VALUES, new ArrayList<String>());
		//propertyViewer.setInput(properties);

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		projectText.setText("");
		scenarioText.setText("");
		propertyViewer.setInput(Collections.EMPTY_LIST);
		
		
		try{
			projectText.setText(configuration.getAttribute(PerfCakeLaunchConstants.PROJECT, ""));
			scenarioText.setText(configuration.getAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, ""));
			List<String> propertyNames =
					configuration.getAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES_NAMES, new ArrayList<String>());
			List<String> propertyValues =
					configuration.getAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES_VALUES, new ArrayList<String>());
			
			if (propertyNames.size() != propertyValues.size()){
				throw new IllegalArgumentException("The list of the propties names and values have not same size");
			}
			
			properties = new ArrayList<>(propertyNames.size());
			for (int i = 0; i < propertyNames.size(); i++){
				SystemProperty p = new SystemProperty();
				p.setName(propertyNames.get(i));
				p.setValue(propertyValues.get(i));
				properties.add(p);
			}
			propertyViewer.setInput(properties);

		} catch (CoreException e){
			log.error("Cannot obtain attributes from launch configuration", e);
			MessageDialog.openError(getShell(), "Cannot obtain configureation", "Launch configuration cannot be loaded from stored attributes.");
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PerfCakeLaunchConstants.PROJECT, projectText.getText());
		configuration.setAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, scenarioText.getText());

		List<String> propertyNames = new ArrayList<>(properties.size());
		List<String> propertyValues = new ArrayList<>(properties.size());
		
		for (SystemProperty p : properties){
			propertyNames.add(p.getName());
			propertyValues.add(p.getValue());
		}

		configuration.setAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES_NAMES, propertyNames);
		configuration.setAttribute(PerfCakeLaunchConstants.PERFCAKE_SYSTEM_PROPERTIES_VALUES, propertyValues);
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
