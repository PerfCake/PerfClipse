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

	private final static int SPINNER_DEFAULT_WIDTH = 33;
	
	private Composite container;

	private Label generatorLabel;
	private Combo generatorCombo;

	private Spinner threadsSpinner;
	private Label threadsLabel;

	private Label runTypeLabel;
	private Combo runTypeCombo;
	
	private Label runValueLabel;
	private Spinner runValueSpinner;
	
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
		generatorLabel.setText("Generator type: ");
		generatorCombo = new Combo(container, SWT.NONE);
		if (components != null && components.getGenerators() != null){
			for (Class<?> clazz : components.getGenerators()){
				generatorCombo.add(clazz.getSimpleName());
			}
		}
		generatorCombo.addSelectionListener(new UpdateSelectionAdapter(this));
		
		generatorCombo.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));

		runTypeLabel = new Label(container, SWT.NONE);
		runTypeLabel.setText("Run type: ");
		
		runTypeCombo = new Combo(container, SWT.NONE);

		//TODO: read possible values from perfcake
		runTypeCombo.add("iteration");
		runTypeCombo.add("time");
		runTypeCombo.add("percentage");
		
		runTypeCombo.addSelectionListener(new UpdateSelectionAdapter(this));

		runTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		runValueLabel = new Label(container, SWT.NONE);
		runValueLabel.setText("Duration: ");
		runValueSpinner = new Spinner(container, SWT.NONE);
		runValueSpinner.setMinimum(0);
		runValueSpinner.setMaximum(Integer.MAX_VALUE);
		runValueSpinner.setSelection(1);

		GridData runSpinnerGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		runSpinnerGridData.widthHint = SPINNER_DEFAULT_WIDTH;
		runValueSpinner.setLayoutData(runSpinnerGridData);

		threadsLabel = new Label(container, SWT.NONE);
		threadsLabel.setText("Number of threads: ");

		threadsSpinner = new Spinner(container, SWT.NONE);
		threadsSpinner.setMinimum(0);
		threadsSpinner.setSelection(1);
		threadsSpinner.setMaximum(Integer.MAX_VALUE);
		GridData threadsSpinnerGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		threadsSpinnerGridData.widthHint = SPINNER_DEFAULT_WIDTH;
		threadsSpinner.setLayoutData(threadsSpinnerGridData);
		
		
		setControl(container);
		setPageComplete(false);
	}
	
	@Override
	protected void updateControls() {
		if (generatorCombo.getText() == null || "".equals(generatorCombo.getText())){
			setDescription("Select generator type!");
			setPageComplete(false);
			return;
		}
		
		if (runTypeCombo.getText() == null || "".equals(runTypeCombo.getText())){
			setDescription("Select run type!");
			setPageComplete(false);
			return;
		}
		setDescription("Complete!");
		setPageComplete(true);
	}

	public String getGeneratorName(){
		return generatorCombo.getText();
	}
	
	public String getRunType(){
		return runTypeCombo.getText();
	}
	
	public int getRunValue(){
		return runValueSpinner.getSelection();
	}
	
	public int getThreads(){
		return threadsSpinner.getSelection();
	}
}
