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

package org.perfclipse.wizards.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.perfcake.model.Property;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.core.model.SenderModel;
import org.perfclipse.core.reflect.PerfCakeComponents;
import org.perfclipse.wizards.WizardUtils;
import org.perfclipse.wizards.swt.ComboUtils;
import org.perfclipse.wizards.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.DoubleClickSelectionAdapter;
import org.perfclipse.wizards.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.wizards.swt.jface.PropertyTableViewer;
import org.perfclipse.wizards.swt.jface.StringComboViewer;
import org.perfclipse.wizards.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class SenderPage extends AbstractPerfCakePage {
	
	public static final String SENDER_PAGE_NAME = "Sender";
	private Composite container;
	private Label senderLabel;
	private StringComboViewer senderTypeViewer;
	
	private TableViewer propertyViewer;
	private TableViewerControl propertyViewerControls;
	
	private SenderModel sender;
	private List<PropertyModel> properties;
	
	public SenderPage(){
		this(SENDER_PAGE_NAME, false);
	}
	
	public SenderPage(SenderModel sender){
		this(SENDER_PAGE_NAME, true);
		this.sender = sender;
		
		ModelMapper mapper = sender.getMapper();

		properties = new ArrayList<>(sender.getProperty().size());

		for (Property p : sender.getProperty()){
			properties.add((PropertyModel) mapper.getModelContainer(p));
		}
	}
	
	private SenderPage(String pageName, boolean edit) {
		super(pageName, edit);
	}
	
	@Override
	public void createControl(Composite parent) {
		setTitle("Sender specification");
		setDescription("Set sender type and sender properties");
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		container.setLayout(layout);
		
		senderLabel = new Label(container, SWT.NONE);
		senderLabel.setText("Choose sender");
		
		PerfCakeComponents components = getPerfCakeComponents();

		senderTypeViewer = new StringComboViewer(container, components.getSenderNames() );

		senderTypeViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		GridData senderComboGridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		senderComboGridData.horizontalSpan = 2;
		senderTypeViewer.getControl().setLayoutData(senderComboGridData);
		
		propertyViewer = new PropertyTableViewer(container, getNestedCommands());
		
		propertyViewerControls = new TableViewerControl(container, true, SWT.NONE);
		GridData tableControlsData = new GridData();
		tableControlsData.verticalAlignment = SWT.BEGINNING;
		propertyViewerControls.setLayoutData(tableControlsData);

		EditPropertySelectionAdapter editPropertyAdapter = new EditPropertySelectionAdapter(getNestedCommands(), propertyViewer);
		propertyViewer.getTable().addMouseListener(new DoubleClickSelectionAdapter(editPropertyAdapter));
		propertyViewerControls.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getNestedCommands(), propertyViewer, sender));
		propertyViewerControls.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(getNestedCommands(), propertyViewer, sender));
		propertyViewerControls.getEditButton().addSelectionListener(editPropertyAdapter);
		
		final Table propertyTable = propertyViewer.getTable();
		GridData propertyTableData = WizardUtils.getTableViewerGridData();
		propertyTableData.horizontalSpan = 2;
		propertyTable.setLayoutData(propertyTableData);
		
		setControl(container);
		super.createControl(parent);
	}
	
	
	
	
	@Override
	protected void fillDefaultValues() {
//		StructuredSelection sel = new StructuredSelection(senderTypeViewer.getElementAt(0));
//		senderTypeViewer.setSelection(sel);
	}

	@Override
	protected void fillCurrentValues() {
		
		if (sender.getSender().getClazz() != null)
			ComboUtils.select(senderTypeViewer, sender.getSender().getClazz());
		
		if (properties != null){
			propertyViewer.setInput(properties);
		}
	}

	@Override
	protected void updateControls() {
		if (getSenderName() == null ||
				"".equals(getSenderName())){
			setDescription("Select sender type!");
			setPageComplete(false);
			return;
		}
		setDescription("Complete!");
		setPageComplete(true);
		super.updateControls();
	}

	public String getSenderName(){
		StructuredSelection sel = (StructuredSelection) senderTypeViewer.getSelection();
		return (String) sel.getFirstElement();
	}

	public TableViewer getPropertyViewer() {
		return propertyViewer;
	}
	
}
