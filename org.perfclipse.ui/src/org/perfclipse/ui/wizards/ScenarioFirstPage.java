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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
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
	private Button scenarioNameBrowseButton;

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
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		container.setLayout(layout);
		
		scenarioNameLabel = new Label(container, SWT.NONE);
		scenarioNameLabel.setText("Name of scenario: ");

		scenarioNameText = new Text(container, SWT.NONE);
		scenarioNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		scenarioNameBrowseButton = new Button(container, SWT.NONE);
		scenarioNameBrowseButton.setText("Browse");
		
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		generatorLabel = new Label(container, SWT.NONE);
		generatorLabel.setText("Choose generator");
		generatorCombo = new Combo(container, SWT.NONE);
		generatorCombo.add("DefaultMessageGenerator");
		generatorCombo.setLayoutData(gridData);
		

		senderLabel = new Label(container, SWT.NONE);
		senderLabel.setText("Choose sender");
		
		senderCombo = new Combo(container, SWT.NONE);
		senderCombo.add("DummySender");
		senderCombo.setLayoutData(gridData);

		setControl(container);
		setPageComplete(true);
		
		
	}

}
