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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.swt.events.AbstractEditCommandSelectionAdapter;
import org.perfclipse.ui.swt.events.AddReporterSelectionAdapter;
import org.perfclipse.ui.swt.events.DeleteReporterSelectionAdapter;
import org.perfclipse.ui.swt.jface.ReporterTableViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;
import org.perfclipse.ui.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.ui.wizards.ReporterEditWizard;

/**
 * @author Jakub Knetl
 *
 */
public class ReportingPage extends AbstractPerfCakePage {

	private static final String REPORTING_PAGE_NAME = "Reporting";

	private ReportingModel reporting;
	private List<ReporterModel> reporters;

	private Composite container;
	private TableViewer reporterViewer;
	private TableViewerControl reporterViewerControl;

	public ReportingPage(){
		super(REPORTING_PAGE_NAME, false);
	}
	
	
	public ReportingPage(ReportingModel reporting){
		super(REPORTING_PAGE_NAME, true);
		this.reporting = reporting;
		reporters = new ArrayList<>(reporting.getReporting().getReporter().size());
		ModelMapper mapper = reporting.getMapper();

		for (Reporter r : reporting.getReporting().getReporter()){
			reporters.add((ReporterModel) mapper.getModelContainer(r));
		}
	}

	private ReportingPage(String pageName, boolean edit) {
		super(pageName, edit);
		setTitle("Reporting");
	}


	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		
		reporterViewer = new ReporterTableViewer(container, getEditingSupportCommands());
		reporterViewerControl = new TableViewerControl(container, true, SWT.NONE);
		reporterViewerControl.getAddButton().addSelectionListener(
				new AddReporterSelectionAdapter(getEditingSupportCommands(), reporterViewer, reporting));
		reporterViewerControl.getEditButton().addSelectionListener(
				new AbstractEditCommandSelectionAdapter(getEditingSupportCommands(), reporterViewer) {
			
			@Override
			protected AbstractPerfCakeEditWizard createWizard(
					IStructuredSelection selection) {
				return new ReporterEditWizard((ReporterModel) selection.getFirstElement());
			}
		});
		reporterViewerControl.getDeleteButton().addSelectionListener(
				new DeleteReporterSelectionAdapter(getEditingSupportCommands(), reporterViewer, reporting));
		final Table reporterTable = reporterViewer.getTable();
		GridData tableData = Utils.getTableViewerGridData();
		reporterTable.setLayoutData(tableData);
		
		setControl(container);
		super.createControl(parent);
	}


	@Override
	protected void updateControls() {
		setPageComplete(true);
		super.updateControls();
	}


	@Override
	protected void fillDefaultValues() {
		// TODO Auto-generated method stub
		super.fillDefaultValues();
	}


	@Override
	protected void fillCurrentValues() {
		reporterViewer.setInput(reporters);
		super.fillCurrentValues();
	}


	public TableViewer getReporterViewer() {
		return reporterViewer;
	}
	
}
