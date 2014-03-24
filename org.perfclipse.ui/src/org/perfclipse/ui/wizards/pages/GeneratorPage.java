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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.perfcake.model.Property;
import org.perfclipse.model.GeneratorModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.swt.ComboUtils;
import org.perfclipse.ui.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.ui.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.ui.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.jface.StringComboViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;
import org.slf4j.LoggerFactory;

public class GeneratorPage extends AbstractPerfCakePage {
	
	private static final String GENERATOR_PAGE_NAME = "Genarator";

	final static org.slf4j.Logger log = LoggerFactory.getLogger(GeneratorPage.class);

	private final static int SPINNER_DEFAULT_WIDTH = 33;
	
	private Composite container;

	private Label generatorLabel;
	private StringComboViewer generatorTypeViewer;

	private Spinner threadsSpinner;
	private Label threadsLabel;

	private Label runTypeLabel;
	private Combo runTypeCombo;
	
	private Label runValueLabel;
	private Spinner runValueSpinner;
	
	private TableViewer propertiesViewer;
	private TableViewerControl propertiesControls;
	
	private GeneratorModel generator;
	private List<PropertyModel> properties;
	

	public GeneratorPage(){
		this(GENERATOR_PAGE_NAME, false);
	}

	public GeneratorPage(GeneratorModel generator){
		this(GENERATOR_PAGE_NAME, true);
		this.generator = generator;
		ModelMapper mapper = generator.getMapper();
		properties = new ArrayList<>(generator.getProperty().size());
		for (Property p : generator.getProperty()){
			properties.add((PropertyModel) mapper.getModelContainer(p));
		}
	}
	
	private GeneratorPage(String pageName, boolean edit) {
		super(pageName, edit);
		setTitle("Generator");
		setDescription("Fill in neccessary information on this page");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		container.setLayout(layout);
		
		PerfCakeComponents components = getPerfCakeComponents();

		generatorLabel = new Label(container, SWT.NONE);
		generatorLabel.setText("Generator type: ");
		
		generatorTypeViewer =  new StringComboViewer(container, components.getGeneratorNames());

		generatorTypeViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		
		GridData generatorComboLayoutData = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
		generatorComboLayoutData.horizontalSpan = 2;
		generatorTypeViewer.getControl().setLayoutData(generatorComboLayoutData);

		runTypeLabel = new Label(container, SWT.NONE);
		runTypeLabel.setText("Run type: ");
		
		runTypeCombo = new Combo(container, SWT.NONE);

		for (String period : Utils.getPeriodTypes()){
			runTypeCombo.add(period);
		}
		
		runTypeCombo.addSelectionListener(new UpdateSelectionAdapter(this));

		GridData runComboLayoutData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		runComboLayoutData.horizontalSpan = 2;
		runTypeCombo.setLayoutData(runComboLayoutData);
		
		runValueLabel = new Label(container, SWT.NONE);
		runValueLabel.setText("Duration: ");
		runValueSpinner = new Spinner(container, SWT.NONE);
		runValueSpinner.setMinimum(0);
		runValueSpinner.setMaximum(Integer.MAX_VALUE);

		GridData runSpinnerGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		runSpinnerGridData.horizontalSpan = 2;
		runSpinnerGridData.widthHint = SPINNER_DEFAULT_WIDTH;
		runValueSpinner.setLayoutData(runSpinnerGridData);

		threadsLabel = new Label(container, SWT.NONE);
		threadsLabel.setText("Number of threads: ");

		threadsSpinner = new Spinner(container, SWT.NONE);
		threadsSpinner.setMinimum(0);
		threadsSpinner.setMaximum(Integer.MAX_VALUE);
		GridData threadsSpinnerGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		threadsSpinnerGridData.horizontalSpan = 2;
		threadsSpinnerGridData.widthHint = SPINNER_DEFAULT_WIDTH;
		threadsSpinner.setLayoutData(threadsSpinnerGridData);
		

		propertiesViewer = new PropertyTableViewer(container, getEditingSupportCommands());
		
		propertiesControls = new TableViewerControl(container, true, SWT.NONE);
		GridData tableControlsData = new GridData();
		tableControlsData.verticalAlignment = SWT.BEGINNING;
		propertiesControls.setLayoutData(tableControlsData);

		propertiesControls.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getEditingSupportCommands(), propertiesViewer, generator));

		propertiesControls.getEditButton().addSelectionListener(
				new EditPropertySelectionAdapter(getEditingSupportCommands(), propertiesViewer));
		propertiesControls.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(getEditingSupportCommands(), propertiesViewer, generator));

		final Table propertiesTable = propertiesViewer.getTable();
		GridData tableData = Utils.getTableViewerGridData();
		tableData.horizontalSpan = 2;
		propertiesTable.setLayoutData(tableData);
		
		setControl(container);
		super.createControl(parent);
	}
	
	@Override
	protected void updateControls() {
		StructuredSelection sel = (StructuredSelection) generatorTypeViewer.getSelection();
		if (generatorTypeViewer.getSelection() == null || 
				"".equals(sel.getFirstElement())){
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
	
	@Override
	protected void fillDefaultValues() {

		ISelection selection = new StructuredSelection(generatorTypeViewer.getElementAt(0));
		generatorTypeViewer.setSelection(selection);
		runTypeCombo.select(0);
		runValueSpinner.setSelection(1);
		threadsSpinner.setSelection(1);
	}

	@Override
	protected void fillCurrentValues() {
		ComboUtils.select(generatorTypeViewer, generator.getGenerator().getClazz());
		
		ComboUtils.select(runTypeCombo, generator.getGenerator().getRun().getType());

		runValueSpinner.setSelection(Integer.valueOf(generator.getGenerator().getRun().getValue()));
		threadsSpinner.setSelection(Integer.valueOf(generator.getGenerator().getThreads()));
		propertiesViewer.setInput(properties);
	}

	public String getGeneratorName(){
		IStructuredSelection sel = (IStructuredSelection) generatorTypeViewer.getSelection();
		return (String) sel.getFirstElement();
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

	public TableViewer getPropertiesViewer() {
		return propertiesViewer;
	}
	
}
