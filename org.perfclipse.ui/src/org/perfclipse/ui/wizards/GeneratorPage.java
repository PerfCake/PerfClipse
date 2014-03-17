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

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.perfcake.common.PeriodType;
import org.perfclipse.model.GeneratorModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.reflect.PerfCakeComponents;
import org.slf4j.LoggerFactory;

public class GeneratorPage extends PerfCakePage {
	
	private static final int COLUMN_WIDTH = 220;

	private static final String GENERATOR_PAGE_NAME = "Scenario genarator and sender";

	final static org.slf4j.Logger log = LoggerFactory.getLogger(GeneratorPage.class);

	private final static int SPINNER_DEFAULT_WIDTH = 33;
	
	private Composite container;

	private Label generatorLabel;
	private Combo generatorCombo;

	private Spinner threadsSpinner;
	private Label threadsLabel;

	private Label runTypeLabel;
	private Combo runTypeCombo;
	
	private Label runValueLabel;
	private Spinner runValueSpinner;
	
	private TableViewer propertiesViewer;
	
	private GeneratorModel generator;
	private List<PropertyModel> properties;
	
	public GeneratorPage(){
		super(GENERATOR_PAGE_NAME);
	}

	public GeneratorPage(GeneratorModel generator, List<PropertyModel> properties){
		super(GENERATOR_PAGE_NAME);
		this.generator = generator;
		this.properties = properties;
	}
	
	public GeneratorPage(String pageName) {
		super(pageName);
		setTitle("Generator");
		setDescription("Fill in neccessary information on this page");
	}

	@Override
	public void createControl(Composite parent) {
		//TODO : gridData constructor with FILL_HORIZONTAL is deprecated
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		container.setLayout(layout);
		
		PerfCakeComponents components = getPerfCakeComponents();

		generatorLabel = new Label(container, SWT.NONE);
		generatorLabel.setText("Generator type: ");
		generatorCombo = new Combo(container, SWT.NONE);
		if (components != null && components.getGenerators() != null){
			for (Class<?> clazz : components.getGenerators()){
				generatorCombo.add(clazz.getSimpleName());
			}
		}
		generatorCombo.select(0);
		generatorCombo.addSelectionListener(new UpdateSelectionAdapter(this));
		
		GridData generatorComboLayoutData = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
		generatorComboLayoutData.horizontalSpan = 2;
		generatorCombo.setLayoutData(generatorComboLayoutData);

		runTypeLabel = new Label(container, SWT.NONE);
		runTypeLabel.setText("Run type: ");
		
		runTypeCombo = new Combo(container, SWT.NONE);

		for (PeriodType period : PeriodType.values()){
			String periodType = period.toString().toLowerCase();
			runTypeCombo.add(periodType);
		}
		
		runTypeCombo.select(0);
		runTypeCombo.addSelectionListener(new UpdateSelectionAdapter(this));

		GridData runComboLayoutData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		runComboLayoutData.horizontalSpan = 2;
		runTypeCombo.setLayoutData(runComboLayoutData);
		
		runValueLabel = new Label(container, SWT.NONE);
		runValueLabel.setText("Duration: ");
		runValueSpinner = new Spinner(container, SWT.NONE);
		runValueSpinner.setMinimum(0);
		runValueSpinner.setMaximum(Integer.MAX_VALUE);
		runValueSpinner.setSelection(1);

		GridData runSpinnerGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		runSpinnerGridData.horizontalSpan = 2;
		runSpinnerGridData.widthHint = SPINNER_DEFAULT_WIDTH;
		runValueSpinner.setLayoutData(runSpinnerGridData);

		threadsLabel = new Label(container, SWT.NONE);
		threadsLabel.setText("Number of threads: ");

		threadsSpinner = new Spinner(container, SWT.NONE);
		threadsSpinner.setMinimum(0);
		threadsSpinner.setSelection(1);
		threadsSpinner.setMaximum(Integer.MAX_VALUE);
		GridData threadsSpinnerGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		threadsSpinnerGridData.horizontalSpan = 2;
		threadsSpinnerGridData.widthHint = SPINNER_DEFAULT_WIDTH;
		threadsSpinner.setLayoutData(threadsSpinnerGridData);
		

		propertiesViewer = new TableViewer(container, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		propertiesViewer.setContentProvider(ArrayContentProvider.getInstance());

		//create columns
		TableViewerColumn keyColumn = new TableViewerColumn(propertiesViewer, SWT.NONE);
		keyColumn.getColumn().setWidth(COLUMN_WIDTH);
		keyColumn.getColumn().setText("Property name");
		keyColumn.setEditingSupport(new PropertyNameEditingSupport(propertiesViewer));
		keyColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				PropertyModel property = (PropertyModel) element;
				return property.getProperty().getName();
			}
			
		});
		TableViewerColumn valueColumn = new TableViewerColumn(propertiesViewer, SWT.NONE);
		valueColumn.getColumn().setText("Property value");
		valueColumn.getColumn().setWidth(COLUMN_WIDTH);
		valueColumn.setEditingSupport(new PropertyValueEditingSupport(propertiesViewer));
		valueColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				PropertyModel property = (PropertyModel) element;
				return property.getProperty().getValue();
			}
			
		});
		
		final Table propertiesTable = propertiesViewer.getTable();
		propertiesTable.setHeaderVisible(true);
		propertiesTable.setLinesVisible(true);
		
		GridData tableData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true);
		tableData.horizontalSpan = 2;
		propertiesTable.setLayoutData(tableData);
		
		// fill in current values if wizard is in edit mode (it means generator already exists)
		if (generator != null){
			
			for (int i = 0; i < generatorCombo.getItems().length; i++) {
				if (generator.getGenerator().getClazz().equals(generatorCombo.getItem(i))){
					generatorCombo.select(i);
					break;
				}
			}
			
			for (int i = 0; i < runTypeCombo.getItems().length; i++) {
				if (generator.getGenerator().getRun().getType().equals(runTypeCombo.getItem(i))){
					runTypeCombo.select(i);
					break;
				}
			}
			
			runValueSpinner.setSelection(Integer.valueOf(generator.getGenerator().getRun().getValue()));
			threadsSpinner.setSelection(Integer.valueOf(generator.getGenerator().getThreads()));
			propertiesViewer.setInput(properties);
		}
		
		setControl(container);
		updateControls();
	}
	
	@Override
	protected void updateControls() {
		if (generatorCombo.getText() == null || "".equals(generatorCombo.getText())){
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

	public String getGeneratorName(){
		return generatorCombo.getText();
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
}
