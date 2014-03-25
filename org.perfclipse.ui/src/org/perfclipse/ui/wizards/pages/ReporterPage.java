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
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.swt.ComboUtils;
import org.perfclipse.ui.swt.events.AbstractEditCommandSelectionAdapter;
import org.perfclipse.ui.swt.events.AddDestinationSelectionAdapter;
import org.perfclipse.ui.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.ui.swt.events.DeleteDestinationSelectionAdapter;
import org.perfclipse.ui.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.ui.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.ui.swt.jface.DestinationTableViewer;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.jface.StringComboViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;
import org.perfclipse.ui.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.ui.wizards.DestinationEditWizard;

/**
 * @author Jakub Knetl
 *
 */
public class ReporterPage extends AbstractPerfCakePage {

	private static final String REPORTER_PAGE_NAME = "Reporter page";
	private ReporterModel reporter;
	private List<DestinationModel> destinations;
	private List<PropertyModel> properties;
	
	private Composite container;
	private Label clazzLabel;
	private StringComboViewer clazzCombo;
	private Label enabledLabel;
	private Button enabledButton;
	private DestinationTableViewer destinationViewer;
	private TableViewerControl destinationControl;
	private PropertyTableViewer propertyViewer;
	private TableViewerControl propertyControl;
	
	/**
	 * 
	 */
	public ReporterPage() {
		this(REPORTER_PAGE_NAME, false);
	}

	public ReporterPage(ReporterModel reporter){
		this(REPORTER_PAGE_NAME, true);
		this.reporter = reporter;
		
		ModelMapper m = reporter.getMapper();
		destinations = new ArrayList<>(reporter.getReporter().getDestination().size());
		for (Destination d : reporter.getReporter().getDestination()){
			destinations.add((DestinationModel) m.getModelContainer(d));
		}
		properties = new ArrayList<>(reporter.getReporter().getProperty().size());
		for (Property p : reporter.getReporter().getProperty()){
			properties.add((PropertyModel) m.getModelContainer(p));
		}
	}
	/**
	 * @param pageName
	 * @param edit
	 */
	private ReporterPage(String pageName, boolean edit) {
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
		clazzLabel.setText("Repoerter type: ");
		clazzCombo = new StringComboViewer(container, getPerfCakeComponents().getReporterNames());
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
		
		destinationViewer = new DestinationTableViewer(container, getEditingSupportCommands());
		data = Utils.getTableViewerGridData();
		data.horizontalSpan = 2;
		destinationViewer.getTable().setLayoutData(data);
		
		destinationControl = new TableViewerControl(container, true, SWT.NONE);
		destinationControl.getAddButton().addSelectionListener(
				new AddDestinationSelectionAdapter(getEditingSupportCommands(), destinationViewer, reporter));
		destinationControl.getEditButton().addSelectionListener(
				new AbstractEditCommandSelectionAdapter(getEditingSupportCommands(), destinationViewer) {
			
			@Override
			protected AbstractPerfCakeEditWizard createWizard(
					IStructuredSelection selection) {
				return new DestinationEditWizard((DestinationModel) selection.getFirstElement());
			}
		});
		destinationControl.getDeleteButton().addSelectionListener(
				new DeleteDestinationSelectionAdapter(getEditingSupportCommands(), destinationViewer, reporter));
		
		propertyViewer = new PropertyTableViewer(container, getEditingSupportCommands());
		data = Utils.getTableViewerGridData();
		data.horizontalSpan = 2;
		propertyViewer.getTable().setLayoutData(data);
		
		propertyControl = new TableViewerControl(container, true, SWT.NONE);
		propertyControl.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getEditingSupportCommands(),
						propertyViewer, reporter));
		propertyControl.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(getEditingSupportCommands(),
						propertyViewer, reporter));
		propertyControl.getDeleteButton().addSelectionListener(
				new EditPropertySelectionAdapter(getEditingSupportCommands(), propertyViewer));
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void updateControls() {
		if ("".equals(getReporterType())){
			setDescription("Select reporter type.");
			setPageComplete(false);
			return;
		}
		setPageComplete(true);
		super.updateControls();
	}

	@Override
	protected void fillCurrentValues() {

		if (reporter.getReporter().getClazz() != null)
			ComboUtils.select(clazzCombo, reporter.getReporter().getClazz());

		enabledButton.setSelection(reporter.getReporter().isEnabled());
		
		if (destinations != null)
			destinationViewer.setInput(destinations);
		if (properties != null)
			propertyViewer.setInput(properties);
		super.fillCurrentValues();
	}
	
	
	
	@Override
	protected void fillDefaultValues() {
		enabledButton.setSelection(true);
		super.fillDefaultValues();
	}

	public String getReporterType(){
		IStructuredSelection sel = (IStructuredSelection) clazzCombo.getSelection();
		return String.valueOf(sel.getFirstElement());
	}
	
	public boolean getEnabled(){
		return enabledButton.getSelection();
	}

	public DestinationTableViewer getDestinationViewer() {
		return destinationViewer;
	}

	public PropertyTableViewer getPropertyViewer(){
		return propertyViewer;
	}
	
}
