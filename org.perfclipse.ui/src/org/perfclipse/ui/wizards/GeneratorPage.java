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

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;
import org.perfcake.common.PeriodType;
import org.perfcake.model.Property;
import org.perfclipse.model.GeneratorModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.ui.gef.commands.AddPropertyCommand;
import org.perfclipse.ui.gef.commands.DeletePropertyCommand;
import org.perfclipse.ui.jface.PropertyTableViewer;
import org.slf4j.LoggerFactory;

public class GeneratorPage extends AbstractPerfCakePage {
	
	private static final String GENERATOR_PAGE_NAME = "Genarator";

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
	private Composite propertiesControls;
	private Button addProperty;
	private Button deleteProperty;
	
	private GeneratorModel generator;
	private List<PropertyModel> properties;
	

	public GeneratorPage(){
		this(GENERATOR_PAGE_NAME, false);
	}

	public GeneratorPage(GeneratorModel generator,
			List<PropertyModel> properties){
		this(GENERATOR_PAGE_NAME, true);
		this.generator = generator;
		this.properties = properties;
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
		generatorCombo = new Combo(container, SWT.NONE);
		if (components != null && components.getGenerators() != null){
			for (Class<?> clazz : components.getGenerators()){
				generatorCombo.add(clazz.getSimpleName());
			}
		}
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
		
		propertiesControls = new Composite(container, SWT.NONE);
		propertiesControls.setLayout(new RowLayout(SWT.VERTICAL));

		addProperty = new Button(propertiesControls, SWT.PUSH);
		addProperty.setText("Add");
		addProperty.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = getDialogInput("Add property", "Property name: ", "name");
				String value = getDialogInput("Add property", "Property value: ", "value");

				if (name == null || value == null)
					return;

				Property p = new org.perfcake.model.ObjectFactory().createProperty();
				p.setName(name);
				p.setValue(value);
				Command c = new AddPropertyCommand(p, generator);
				c.execute();
				getEditingSupportCommands().add(c);

				//TODO: obtain ModelMapper
				propertiesViewer.add(new PropertyModel(p));
			}
			
			private String getDialogInput(String title, String label, String initValue) {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				//TODO: validator
				InputDialog dialog = new InputDialog(shell, title, label, initValue, null);
				dialog.open();
				if (dialog.getReturnCode() == InputDialog.OK)
					return dialog.getValue();
				
				return null;
			}

		});

		deleteProperty = new Button(propertiesControls, SWT.PUSH);
		deleteProperty.setText("Delete");
		deleteProperty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				ISelection selection = propertiesViewer.getSelection();
				if (selection instanceof IStructuredSelection){
					@SuppressWarnings("unchecked")
					Iterator<Object> it = ((IStructuredSelection) selection).iterator();
					while (it.hasNext()){
						PropertyModel property = (PropertyModel) it.next();
						Command c = new DeletePropertyCommand(generator, property);
						c.execute();
						getEditingSupportCommands().add(c);
						propertiesViewer.remove(property);
					}
					
				}
			}
			
		});

		final Table propertiesTable = propertiesViewer.getTable();
		GridData tableData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true);
		tableData.horizontalSpan = 2;
		propertiesTable.setLayoutData(tableData);
		
		fillValues();
		
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
	
	@Override
	protected void fillDefaultValues() {
		generatorCombo.select(0);
		runTypeCombo.select(0);
		runValueSpinner.setSelection(1);
		threadsSpinner.setSelection(1);
	}

	@Override
	protected void fillCurrentValues() {
		ComboUtils.select(generatorCombo, generator.getGenerator().getClazz());
		
		ComboUtils.select(runTypeCombo, generator.getGenerator().getRun().getType());

		runValueSpinner.setSelection(Integer.valueOf(generator.getGenerator().getRun().getValue()));
		threadsSpinner.setSelection(Integer.valueOf(generator.getGenerator().getThreads()));
		propertiesViewer.setInput(properties);
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
