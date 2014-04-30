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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfclipse.core.model.DestinationModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.PeriodModel;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.wizards.PeriodEditWizard;
import org.perfclipse.wizards.WizardUtils;
import org.perfclipse.wizards.swt.ComboUtils;
import org.perfclipse.wizards.swt.events.AbstractCommandSelectionAdapter;
import org.perfclipse.wizards.swt.events.AbstractEditCommandSelectionAdapter;
import org.perfclipse.wizards.swt.events.AddPeriodSelectionAdapter;
import org.perfclipse.wizards.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.DelKeyPressedSelectionAdapter;
import org.perfclipse.wizards.swt.events.DeletePeriodSelectionAdapter;
import org.perfclipse.wizards.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.DoubleClickSelectionAdapter;
import org.perfclipse.wizards.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.wizards.swt.jface.PeriodTableViewer;
import org.perfclipse.wizards.swt.jface.PropertyTableViewer;
import org.perfclipse.wizards.swt.jface.StringComboViewer;
import org.perfclipse.wizards.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class DestinationPage extends AbstractPerfCakePage {

	public static final String DESTINATION_PAGE_NAME = "Destination page";
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
		if (destination == null){
			throw new IllegalArgumentException("Destination model must not be null");
		}
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
		setTitle("Destination");
		setDescription("Fill in values.");
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
		
		periodViewer = new PeriodTableViewer(container, getNestedCommands());
		data = WizardUtils.getTableViewerGridData();
		data.horizontalSpan = 2;
		periodViewer.getTable().setLayoutData(data);

		AbstractCommandSelectionAdapter editPeriodSelectionAdapter = 
				new AbstractEditCommandSelectionAdapter(getNestedCommands(), periodViewer) {
			
			@Override
			protected AbstractPerfCakeEditWizard createWizard(
					IStructuredSelection selection) {
				return new PeriodEditWizard((PeriodModel) selection.getFirstElement());
			}
		};

		periodViewer.getTable().addMouseListener(new DoubleClickSelectionAdapter(editPeriodSelectionAdapter));
		
		periodControl = new TableViewerControl(container, true, SWT.NONE);
		periodControl.getAddButton().addSelectionListener(
				new AddPeriodSelectionAdapter(getNestedCommands(), periodViewer, destination));


		periodControl.getEditButton().addSelectionListener(editPeriodSelectionAdapter);
		DeletePeriodSelectionAdapter deletePeriodAdapter = new DeletePeriodSelectionAdapter(getNestedCommands(), periodViewer, destination);
		periodViewer.getTable().addKeyListener(new DelKeyPressedSelectionAdapter(deletePeriodAdapter));
		periodControl.getDeleteButton().addSelectionListener(deletePeriodAdapter);
			
		
		propertyViewer = new PropertyTableViewer(container, getNestedCommands());
		data = WizardUtils.getTableViewerGridData();
		data.horizontalSpan = 2;
		propertyViewer.getTable().setLayoutData(data);
		
		EditPropertySelectionAdapter editPropertyAdapter = new EditPropertySelectionAdapter(getNestedCommands(), propertyViewer);
		propertyViewer.getTable().addMouseListener(new DoubleClickSelectionAdapter(editPropertyAdapter));
		propertyControl = new TableViewerControl(container, true, SWT.NONE);
		propertyControl.getAddButton().addSelectionListener(new AddPropertySelectionAdapter(getNestedCommands(), propertyViewer, destination));
		propertyControl.getEditButton().addSelectionListener(editPropertyAdapter);
		DeletePropertySelectionAdapter deletePropertyAdapter = new DeletePropertySelectionAdapter(getNestedCommands(), propertyViewer, destination);
		propertyViewer.getTable().addKeyListener(new DelKeyPressedSelectionAdapter(deletePropertyAdapter));
		propertyControl.getDeleteButton().addSelectionListener(deletePropertyAdapter);
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void updateControls() {
		if (getDestinationType() == null || "".equals(getDestinationType())){
			setDescription("Select destination type.");
			setPageComplete(false);
			return;
		}
		setDescription("Complete!");
		setPageComplete(true);
		super.updateControls();
	}

	@Override
	protected void fillCurrentValues() {

		if (destination.getDestination().getClazz() != null)
			ComboUtils.select(clazzCombo, destination.getDestination().getClazz());

		enabledButton.setSelection(destination.getDestination().isEnabled());
		
		if (periods != null)
			periodViewer.setInput(periods);
		if (properties != null)
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
		return (String) sel.getFirstElement();
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
