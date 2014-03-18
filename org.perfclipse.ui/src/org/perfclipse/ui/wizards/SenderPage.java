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

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.SenderModel;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.ui.swt.ComboUtils;
import org.perfclipse.ui.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.ui.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jakub Knetl
 *
 */
public class SenderPage extends AbstractPerfCakePage {
	
	private static final String SENDER_PAGE_NAME = "Sender";
	private Composite container;
	private Label senderLabel;
	private Combo senderCombo;
	static final Logger log = LoggerFactory.getLogger(SenderPage.class);
	
	private TableViewer propertyViewer;
	private TableViewerControl propertyViewerControls;
	
	private SenderModel sender;
	private List<PropertyModel> properties;
	
	public SenderPage(){
		this(SENDER_PAGE_NAME, false);
	}
	
	public SenderPage(SenderModel sender, List<PropertyModel> properties){
		this(SENDER_PAGE_NAME, true);
		this.sender = sender;
		this.properties = properties;
	}
	
	private SenderPage(String pageName, boolean edit) {
		super(pageName, edit);
		setTitle("Sender specification");
		setDescription("Set sender type and sender properties");
	}
	
	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
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

		GridData senderComboGridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		senderComboGridData.horizontalSpan = 2;
		senderCombo.setLayoutData(senderComboGridData);
		
		propertyViewer = new PropertyTableViewer(container, getEditingSupportCommands());
		final Table propertyTable = propertyViewer.getTable();
		GridData propertyTableGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true);
		propertyTableGridData.horizontalSpan = 2;
		propertyTable.setLayoutData(propertyTableGridData);
		
		propertyViewerControls = new TableViewerControl(container, false, SWT.NONE);
		propertyViewerControls.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(propertyViewer, getEditingSupportCommands(), sender));
		propertyViewerControls.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(propertyViewer, getEditingSupportCommands(), sender));
		fillValues();

		setControl(container);
		updateControls();
	}
	
	
	
	@Override
	protected void fillDefaultValues() {
		senderCombo.select(0);
	}

	@Override
	protected void fillCurrentValues() {
		ComboUtils.select(senderCombo, sender.getSender().getClazz());
		
		if (properties != null){
			propertyViewer.setInput(properties);
		}
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
