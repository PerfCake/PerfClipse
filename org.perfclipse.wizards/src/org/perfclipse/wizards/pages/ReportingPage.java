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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ReportingModel;
import org.perfclipse.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.wizards.ReporterEditWizard;
import org.perfclipse.wizards.WizardUtils;
import org.perfclipse.wizards.swt.events.AbstractEditCommandSelectionAdapter;
import org.perfclipse.wizards.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.AddReporterSelectionAdapter;
import org.perfclipse.wizards.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.DeleteReporterSelectionAdapter;
import org.perfclipse.wizards.swt.events.DoubleClickSelectionAdapter;
import org.perfclipse.wizards.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.wizards.swt.jface.PropertyTableViewer;
import org.perfclipse.wizards.swt.jface.ReporterTableViewer;
import org.perfclipse.wizards.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class ReportingPage extends AbstractPerfCakePage {

	public static final String REPORTING_PAGE_NAME = "Reporting";

	private ReportingModel reporting;
	private List<ReporterModel> reporters;
	private List<PropertyModel> properties;

	private Composite container;
	private TableViewer reporterViewer;
	private TableViewerControl reporterViewerControl;

	private TableViewer propertyViewer;
	private TableViewerControl propertyControl;
	public ReportingPage(){
		super(REPORTING_PAGE_NAME, false);
	}
	
	
	public ReportingPage(ReportingModel reporting){
		super(REPORTING_PAGE_NAME, true);
		this.reporting = reporting;
		
		if (reporting.getReporting() == null){
			setEditMode(false);
			return;
		}
		reporters = new ArrayList<>(reporting.getReporting().getReporter().size());
		ModelMapper mapper = reporting.getMapper();

		for (Reporter r : reporting.getReporting().getReporter()){
			reporters.add((ReporterModel) mapper.getModelContainer(r));
		}
		
		properties = new ArrayList<>(reporting.getProperty().size());
		for (Property p : reporting.getProperty()){
			properties.add((PropertyModel) mapper.getModelContainer(p));
		}
	}

	private ReportingPage(String pageName, boolean edit) {
		super(pageName, edit);
	}


	@Override
	public void createControl(Composite parent) {
		setTitle("Reporting");
		setDescription("Manage reporters and properties.");
		container = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		GridData data;
		
		reporterViewer = new ReporterTableViewer(container, getNestedCommands());
		AbstractEditCommandSelectionAdapter editReporterAdapter = 
				new AbstractEditCommandSelectionAdapter(getNestedCommands(), reporterViewer) {
			
			@Override
			protected AbstractPerfCakeEditWizard createWizard(
					IStructuredSelection selection) {
				return new ReporterEditWizard((ReporterModel) selection.getFirstElement());
			}
		};
		
		reporterViewer.getTable().addMouseListener(new  DoubleClickSelectionAdapter(editReporterAdapter));
				
		reporterViewerControl = new TableViewerControl(container, true, SWT.NONE);
		reporterViewerControl.getAddButton().addSelectionListener(
				new AddReporterSelectionAdapter(getNestedCommands(), reporterViewer, reporting));
		reporterViewerControl.getEditButton().addSelectionListener(editReporterAdapter);
		reporterViewerControl.getDeleteButton().addSelectionListener(
				new DeleteReporterSelectionAdapter(getNestedCommands(), reporterViewer, reporting));
		final Table reporterTable = reporterViewer.getTable();
		GridData tableData = WizardUtils.getTableViewerGridData();
		reporterTable.setLayoutData(tableData);
		
		
		propertyViewer = new PropertyTableViewer(container, getNestedCommands());
		propertyViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = WizardUtils.getTableViewerGridData();
		propertyViewer.getTable().setLayoutData(data);
		
		EditPropertySelectionAdapter editPropertyAdapter = new EditPropertySelectionAdapter(getNestedCommands(), propertyViewer);
		propertyViewer.getTable().addMouseListener(new DoubleClickSelectionAdapter(editPropertyAdapter));
		propertyControl = new TableViewerControl(container, true, SWT.NONE);
		propertyControl.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getNestedCommands(),
						propertyViewer, reporting));
		propertyControl.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(getNestedCommands(),
						propertyViewer, reporting));
		propertyControl.getEditButton().addSelectionListener(editPropertyAdapter);
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void updateControls() {
		setPageComplete(true);
		super.updateControls();
	}

	@Override
	protected void fillCurrentValues() {
		if (reporters != null)
			reporterViewer.setInput(reporters);
		super.fillCurrentValues();
	}

	public TableViewer getReporterViewer() {
		return reporterViewer;
	}
	
	public TableViewer getPropertyViewer(){
		return propertyViewer;
	}
}
