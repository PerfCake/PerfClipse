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

public class ScenarioFirstPage extends WizardPage {

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

	public ScenarioFirstPage(){
		this("Scenario first page");
	}
	
	public ScenarioFirstPage(String pageName) {
		super(pageName);
		setTitle("Create new PerfCake scenario");
		setDescription("Fill neccessary information on this page");
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
		scenarioDirectoryText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		scenarioDirectoryBrowseButton = new Button(container, SWT.NONE);
		scenarioDirectoryBrowseButton.setText("Browse");
		scenarioDirectoryBrowseButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
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

}
