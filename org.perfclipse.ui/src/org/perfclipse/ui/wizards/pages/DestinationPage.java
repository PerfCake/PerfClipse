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

package org.perfclipse.ui.wizards.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PeriodModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.swt.ComboUtils;
import org.perfclipse.ui.swt.jface.PeriodTableViewer;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.jface.StringComboViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class DestinationPage extends AbstractPerfCakePage {

	private static final String DESTINATION_PAGE_NAME = "Destination page";
	private DestinationModel destination;
	private List<PeriodModel> periods;
	private List<PropertyModel> properties;
	
	private Composite container;
	private Label clazzLabel;
	private StringComboViewer clazzCombo;
	private Label enabledLabel;
	private Button enabledButton;
	private PeriodTableViewer periodViewer;
	private TableViewerControl periodControl;
	private PropertyTableViewer propertyViewer;
	private TableViewerControl propertyControl;
	
	/**
	 * 
	 */
	public DestinationPage() {
		this(DESTINATION_PAGE_NAME, false);
	}

	public DestinationPage(DestinationModel destination){
		this(DESTINATION_PAGE_NAME, true);
		this.destination = destination;
		
		ModelMapper m = destination.getMapper();
		periods = new ArrayList<>(destination.getDestination().getPeriod().size());
		for (Period p : destination.getDestination().getPeriod()){
			periods.add((PeriodModel) m.getModelContainer(p));
		}
		properties = new ArrayList<>(destination.getDestination().getProperty().size());
		for (Property p : destination.getDestination().getProperty()){
			properties.add((PropertyModel) m.getModelContainer(p));
		}
	}
	/**
	 * @param pageName
	 * @param edit
	 */
	private DestinationPage(String pageName, boolean edit) {
		super(pageName, edit);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		container.setLayout(layout);
		GridData data;
		
		clazzLabel = new Label(container, SWT.NONE);
		clazzLabel.setText("Destination type: ");
		clazzCombo = new StringComboViewer(container, getPerfCakeComponents().getDestinationNames());
		clazzCombo.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		clazzCombo.getControl().setLayoutData(data);
		
		enabledLabel = new  Label(container, SWT.NONE);
		enabledLabel.setText("Enabled: ");
		enabledButton = new Button(container, SWT.CHECK);
		data = new GridData();
		data.horizontalAlignment = SWT.LEFT;
		data.horizontalSpan = 2;
		enabledButton.setLayoutData(data);
		
		periodViewer = new PeriodTableViewer(container, getEditingSupportCommands());
		data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		periodViewer.getTable().setLayoutData(data);
		
		periodControl = new TableViewerControl(container, false, SWT.NONE);
		
		propertyViewer = new PropertyTableViewer(container, getEditingSupportCommands());
		data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		propertyViewer.getTable().setLayoutData(data);
		
		
		propertyControl = new TableViewerControl(container, false, SWT.NONE);
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void updateControls() {
		if ("".equals(getDestinationType())){
			setDescription("Select destination type.");
			setPageComplete(false);
			return;
		}
		setPageComplete(true);
		super.updateControls();
	}

	@Override
	protected void fillCurrentValues() {

		ComboUtils.select(clazzCombo, destination.getDestination().getClazz());
		enabledButton.setSelection(destination.getDestination().isEnabled());
		
		periodViewer.setInput(periods);
		propertyViewer.setInput(properties);
		super.fillCurrentValues();
	}
	
	
	
	@Override
	protected void fillDefaultValues() {
		enabledButton.setSelection(true);
		super.fillDefaultValues();
	}

	public String getDestinationType(){
		IStructuredSelection sel = (IStructuredSelection) clazzCombo.getSelection();
		return String.valueOf(sel.getFirstElement());
	}
	
	public boolean getEnabled(){
		return enabledButton.getSelection();
	}

	public PeriodTableViewer getPeriodViewer() {
		return periodViewer;
	}

	public PropertyTableViewer getPropertyViewer() {
		return propertyViewer;
	}
}
