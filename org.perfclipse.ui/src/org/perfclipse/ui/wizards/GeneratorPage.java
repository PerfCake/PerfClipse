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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.perfclipse.reflect.PerfCakeComponents;
import org.slf4j.LoggerFactory;

public class GeneratorPage extends PerfCakePage {
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(GeneratorPage.class);
	
	private Composite container;

	private Label generatorLabel;
	private Combo generatorCombo;

	private Spinner threadsSpinner;
	private Label threadsLabel;

	private Label runLabel;
	private Combo runCombo;
	
	public GeneratorPage(){
		this("Scenario genarator and sender");
	}
	
	public GeneratorPage(String pageName) {
		super(pageName);
		setTitle("Generator");
		setDescription("Fill in neccessary information on this page");
	}

	@Override
	public void createControl(Composite parent) {
		//TODO : gridData constructor with FILL_HORIZONTAL is deprecated
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		container.setLayout(layout);
		
		PerfCakeComponents components = getPerfCakeComponents();

		generatorLabel = new Label(container, SWT.NONE);
		generatorLabel.setText("Choose generator");
		generatorCombo = new Combo(container, SWT.NONE);
		if (components != null && components.getGenerators() != null){
			for (Class<?> clazz : components.getGenerators()){
				generatorCombo.add(clazz.getSimpleName());
			}
		}
		generatorCombo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateControls();
			}
		});
		
		generatorCombo.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));
		
		threadsLabel = new Label(container, SWT.NONE);
		threadsLabel.setText("Number of threads: ");

		threadsSpinner = new Spinner(container, SWT.NONE);
		//TODO: set size of spinner
		
		runLabel = new Label(container, SWT.NONE);
		runLabel.setText("Run type: ");
		
		runCombo = new Combo(container, SWT.NONE);

		//TODO: read possible values from perfcake
		runCombo.add("iteration");
		runCombo.add("time");
		runCombo.add("percentage");
		
		setControl(container);
		setPageComplete(false);
	}
	
	@Override
	protected void updateControls() {
		if (generatorCombo.getText() == null || "".equals(generatorCombo.getText())){
			setDescription("Select generator type!");
			setPageComplete(false);
		}else{
			setDescription("Complete!");
			setPageComplete(true);
		}
	}

	public String getGeneratorName(){
		return generatorCombo.getText();
	}
}
