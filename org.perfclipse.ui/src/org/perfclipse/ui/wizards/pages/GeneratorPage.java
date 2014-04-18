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
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.perfcake.model.Property;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.model.GeneratorModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.core.reflect.PerfCakeComponents;
import org.perfclipse.ui.Activator;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.swt.ComboUtils;
import org.perfclipse.ui.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.ui.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.ui.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.jface.StringComboViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;

public class GeneratorPage extends AbstractPerfCakePage {
	
	public static final String GENERATOR_PAGE_NAME = "Genarator";

	static final Logger log = Activator.getDefault().getLogger();

	private final static int SPINNER_DEFAULT_WIDTH = 33;
	
	private Composite container;

	private Label generatorLabel;
	private StringComboViewer generatorTypeViewer;

	private Text threadsText;
	private Label threadsLabel;

	private Label runTypeLabel;
	private Combo runTypeCombo;
	
	private Label runValueLabel;
	private Text runValueText;
	
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

	}

	@Override
	public void createControl(Composite parent) {
		setTitle("Generator");
		setDescription("Fill in neccessary information on this page");
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
		runValueText = new Text(container, SWT.NONE);
		runValueText.addModifyListener(new UpdateModifyListener(this));

		GridData runSpinnerGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		runSpinnerGridData.horizontalSpan = 2;
		runSpinnerGridData.horizontalAlignment = SWT.FILL;
		runSpinnerGridData.widthHint = SPINNER_DEFAULT_WIDTH;
		runValueText.setLayoutData(runSpinnerGridData);

		threadsLabel = new Label(container, SWT.NONE);
		threadsLabel.setText("Number of threads: ");

		threadsText = new Text(container, SWT.NONE);
		threadsText.addModifyListener(new UpdateModifyListener(this));
		GridData threadsSpinnerGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		threadsSpinnerGridData.horizontalSpan = 2;
		threadsSpinnerGridData.horizontalAlignment = SWT.FILL;
		threadsSpinnerGridData.widthHint = SPINNER_DEFAULT_WIDTH;
		threadsText.setLayoutData(threadsSpinnerGridData);
		

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
		if (threadsText.getText() == null || "".equals(threadsText.getText())){
			setDescription("Fill in number of threads.");
			setPageComplete(false);
			return;
		}
		if (runValueText.getText() == null || "".equals(runValueText.getText())){
			setDescription("Fill in run length.");
			setPageComplete(false);
			return;
		}
		
		setDescription("Complete!");
		setPageComplete(true);
	}
	
	@Override
	protected void fillCurrentValues() {
		if (generator.getGenerator().getClazz() != null)
			ComboUtils.select(generatorTypeViewer, generator.getGenerator().getClazz());
		
		if (generator.getGenerator().getRun() != null)
			ComboUtils.select(runTypeCombo, generator.getGenerator().getRun().getType());

		if (generator.getGenerator().getRun().getValue() != null)
			runValueText.setText(generator.getGenerator().getRun().getValue());
		if (generator.getGenerator().getThreads() != null)
			threadsText.setText(generator.getGenerator().getThreads());

		if (properties != null)
			propertiesViewer.setInput(properties);
	}

	public String getGeneratorName(){
		IStructuredSelection sel = (IStructuredSelection) generatorTypeViewer.getSelection();
		return (String) sel.getFirstElement();
	}
	
	public String getRunType(){
		return runTypeCombo.getText();
	}
	
	public String getRunValue(){
		return runValueText.getText();
	}
	
	public String getThreads(){
		return threadsText.getText();
	}

	public TableViewer getPropertiesViewer() {
		return propertiesViewer;
	}
	
}
