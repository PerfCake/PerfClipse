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
import org.perfclipse.reflect.PerfCakeComponents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jakub Knetl
 *
 */
public class SenderPage extends PerfCakePage {
	
	private Composite container;
	private Label senderLabel;
	private Combo senderCombo;
	static final Logger log = LoggerFactory.getLogger(SenderPage.class);
	
	public SenderPage(){
		this("Sender page");
	}
	public SenderPage(String pageName) {
		super(pageName);
		setTitle("Sender specification");
		setDescription("Set sender type and sender properties");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		container.setLayout(layout);
		
		senderLabel = new Label(container, SWT.NONE);
		senderLabel.setText("Choose sender");
		
		senderCombo = new Combo(container, SWT.NONE);
		
		PerfCakeComponents components = getPerfCakeComponents();
		
		if (components != null && components.getSenders() != null){
			for (Class<?> clazz : components.getSenders()){
				senderCombo.add(clazz.getSimpleName());
			}
		}
		senderCombo.add("DummySender");
		senderCombo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateControls();
			}
		});

		GridData gridData = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
		senderCombo.setLayoutData(gridData);

		setControl(container);
		setPageComplete(false);
	}
	
	
	
	@Override
	protected void updateControls() {
		if (senderCombo.getText() == null || "".equals(senderCombo.getText())){
			setDescription("Select sender type!");
			setPageComplete(false);
		}else{
			setDescription("Complete!");
			setPageComplete(true);
		}
		

		super.updateControls();
	}

	public String getSenderName(){
		return senderCombo.getText();
	}
	
}
