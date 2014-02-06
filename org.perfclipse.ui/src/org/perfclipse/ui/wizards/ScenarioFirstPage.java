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

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;

public class ScenarioFirstPage extends WizardPage {
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(ScenarioFirstPage.class);
	
	private Composite container;
	private Label scenarioNameLabel;
	private Text scenarioNameText;
	private Label senderLabel;
	private Combo senderCombo;
	private Label generatorLabel;
	private Combo generatorCombo;
	private Button scenarioDirectoryBrowseButton;
	private Label scenarioDirectoryLabel;
	private Text scenarioDirectoryText;
	
	String defaultDirectoryPath;

	public ScenarioFirstPage(IStructuredSelection selection){
		this("Scenario first page", selection);
	}
	
	public ScenarioFirstPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		setTitle("Create new PerfCake scenario");
		setDescription("Fill neccessary information on this page");
		
		//TODO : check if the path is correctly resolved on the windows machine
		defaultDirectoryPath = getDefaultDirectoryPath(selection).getPath();
	}



	@Override
	public void createControl(Composite parent) {
		//TODO : gridData constructor with FILL_HORIZONTAL is deprecated
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		container.setLayout(layout);
		
		scenarioDirectoryLabel = new Label(container, SWT.NONE);
		scenarioDirectoryLabel.setText("Choose scenario directory");

		scenarioDirectoryText = new Text(container, SWT.NONE);
		scenarioDirectoryText.setEditable(false);
		scenarioDirectoryText.setText(defaultDirectoryPath);
		scenarioDirectoryText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		scenarioDirectoryBrowseButton = new Button(container, SWT.NONE);
		scenarioDirectoryBrowseButton.setText("Browse");
		scenarioDirectoryBrowseButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setFilterPath(defaultDirectoryPath);
				String result = dialog.open();
				scenarioDirectoryText.setText(result);
				setPageComplete(isPageComplete());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});		
		
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;

		scenarioNameLabel = new Label(container, SWT.NONE);
		scenarioNameLabel.setText("Name of scenario: ");

		scenarioNameText = new Text(container, SWT.NONE);
		scenarioNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scenarioNameText.setLayoutData(gridData);
		scenarioNameText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				setPageComplete(isPageComplete());
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		

		generatorLabel = new Label(container, SWT.NONE);
		generatorLabel.setText("Choose generator");
		generatorCombo = new Combo(container, SWT.NONE);
		generatorCombo.add("DefaultMessageGenerator");
		generatorCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(isPageComplete());
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		generatorCombo.setLayoutData(gridData);
		

		senderLabel = new Label(container, SWT.NONE);
		senderLabel.setText("Choose sender");
		
		senderCombo = new Combo(container, SWT.NONE);
		senderCombo.add("DummySender");
		senderCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(isPageComplete());
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		senderCombo.setLayoutData(gridData);

		
		setControl(container);
		setPageComplete(false);
		
		
	}
	
	@Override
	public boolean isPageComplete(){
		if (scenarioDirectoryText == null || "".equals(scenarioDirectoryText.getText())){
			setDescription("Fill in scenario directory location.");
			return false;
		}
		if (scenarioNameText.getText() == null || "".equals(scenarioNameText.getText())){
			setDescription("Fill in scenario name");
			return false;
		}
		if (generatorCombo.getText() == null || "".equals(generatorCombo.getText())){
			setDescription("Select generator type!");
			return false;
		}
		if (senderCombo.getText() == null || "".equals(senderCombo.getText())){
			setDescription("Select sender type!");
			return false;
		}
		//if file with given name already exists
		String path = scenarioDirectoryText.getText() + File.separator + scenarioNameText.getText() + ".xml";
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		URI uri;
		try {
			uri = new URI("file://" + path);
			IFile[] file = workspaceRoot.findFilesForLocationURI(uri);
			if (file.length > 0 && file[0].exists()){
				setDescription("File already exits! Choose another file name.");
				return false;
			}
		} catch (URISyntaxException e) {
			log.error("Wrong URI syntax", e);
			MessageDialog.openError(getShell(), "Wrong URI syntax", "Filename URI is invalid");
		}
		

		setDescription("Complete!");
		return true;
	}
	
	public String getScenarioName(){
		return scenarioNameText.getText();
	}
	
	public String getScenarioDirectory(){
		return scenarioDirectoryText.getText();
	}
	
	public String getSenderName(){
		return senderCombo.getText();
	}
	
	public String getGeneratorName(){
		return generatorCombo.getText();
	}
	
	
	private URI getDefaultDirectoryPath(IStructuredSelection selection) {
		if (selection != null && selection.getFirstElement() instanceof IResource){
			if (selection.getFirstElement() instanceof IProject){
				IProject project = (IProject) selection.getFirstElement();
				IResource scenarioDir = project.findMember("scenarios");
				if (scenarioDir instanceof IFolder){
					return scenarioDir.getLocationURI();
				}
			}
			if (selection.getFirstElement() instanceof IFolder){
				return ((IFolder) selection.getFirstElement()).getLocationURI();
			}
			if (selection.getFirstElement() instanceof IFile){
				return ((IFile) selection.getFirstElement()).getParent().getLocationURI();
			}
		}
		return ResourcesPlugin.getWorkspace().getRoot().getLocationURI();
	}

}
