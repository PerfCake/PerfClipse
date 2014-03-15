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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.reflect.PerfClipseScannerException;
import org.slf4j.LoggerFactory;

public class GeneratorPage extends WizardPage {
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(GeneratorPage.class);
	
	private Composite container;
	private Label generatorLabel;
	private Combo generatorCombo;
	
	public GeneratorPage(){
		this("Scenario genarator and sender");
	}
	
	public GeneratorPage(String pageName) {
		super(pageName);
		setTitle("Generator and sender");
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
		
		
		PerfCakeComponents components = null;
		try {
			components = PerfCakeComponents.getInstance();
		} catch (PerfClipseScannerException e) {
			log.error("Cannot parse PerfCake components", e);
			MessageDialog.openError(getShell(), "Cannot parse PerfCake components",
					"Automatically loaded components from PerfCake will not be available");
		}

		generatorLabel = new Label(container, SWT.NONE);
		generatorLabel.setText("Choose generator");
		generatorCombo = new Combo(container, SWT.NONE);
		if (components != null && components.getGenerators() != null){
			for (Class<?> clazz : components.getGenerators()){
				generatorCombo.add(clazz.getSimpleName());
			}
		}
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
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		generatorCombo.setLayoutData(gridData);
		
		setControl(container);
		setPageComplete(false);
		
		
	}
	
	@Override
	public boolean isPageComplete(){
		if (generatorCombo.getText() == null || "".equals(generatorCombo.getText())){
			setDescription("Select generator type!");
			return false;
		}

		setDescription("Complete!");
		return true;
	}

	
	public String getGeneratorName(){
		return generatorCombo.getText();
	}
	
	
}
