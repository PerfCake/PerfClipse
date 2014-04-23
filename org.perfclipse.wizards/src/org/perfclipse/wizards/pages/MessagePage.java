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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.core.model.HeaderModel;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.core.model.ValidatorRefModel;
import org.perfclipse.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.wizards.HeaderEditWizard;
import org.perfclipse.wizards.WizardUtils;
import org.perfclipse.wizards.swt.events.AbstractEditCommandSelectionAdapter;
import org.perfclipse.wizards.swt.events.AddHeaderSelectionAdapter;
import org.perfclipse.wizards.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.AttachValidatorSelectionAdapter;
import org.perfclipse.wizards.swt.events.DeleteHeaderSelectionAdapter;
import org.perfclipse.wizards.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.DetachValidatorSelectionAdapter;
import org.perfclipse.wizards.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.wizards.swt.jface.HeaderTableViewer;
import org.perfclipse.wizards.swt.jface.PropertyTableViewer;
import org.perfclipse.wizards.swt.jface.ValidatorRefTableViewer;
import org.perfclipse.wizards.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class MessagePage extends AbstractPerfCakePage {



	public static final String MESSAGE_PAGE_NAME = "Message";

	private MessageModel message;
	private List<HeaderModel> headers;
	private List<PropertyModel> properties;
	private List<ValidatorRefModel> refs;

	private Label uriLabel;
	private Text uriText;
	private Label multiplicityLabel;
	private Spinner multiplicitySpinner;

	private Composite container;
	private HeaderTableViewer headerViewer;
	private PropertyTableViewer propertyViewer;
	private ValidatorRefTableViewer refViewer;
	
	private TableViewerControl headerControl;
	private TableViewerControl propertyControl;
	private TableViewerControl refControl;
	
	//list of validators which will be possibly created and which are not contained in model.
	private List<ValidatorModel> validators;
	
	public MessagePage(){
		this(MESSAGE_PAGE_NAME, false);
	}

	public MessagePage(MessageModel message){
		this(MESSAGE_PAGE_NAME, true);
		this.message = message;
		
		ModelMapper mapper = message.getMapper();
		
		properties = new ArrayList<>(message.getProperty().size());
		headers = new ArrayList<>(message.getMessage().getHeader().size());
		refs = new ArrayList<>(message.getMessage().getValidatorRef().size());
		
		for (Property p : message.getProperty()){
			properties.add((PropertyModel) mapper.getModelContainer(p));
		}
		
		for (Header h : message.getMessage().getHeader()){
			headers.add((HeaderModel) mapper.getModelContainer(h));
		}
		
		for (ValidatorRef r : message.getMessage().getValidatorRef()){
			refs.add((ValidatorRefModel) mapper.getModelContainer(r));
		}
	}

	private MessagePage(String pageName, boolean edit) {
		super(pageName, edit);
	}

	@Override
	public void createControl(Composite parent) {
		setTitle("Message");
		setDescription("Fill in message data.");
		container = new Composite(parent, SWT.NONE);
		
		GridData data;
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		container.setLayout(layout);
		
		
		uriLabel = new Label(container, SWT.NONE);
		uriLabel.setText("URI: ");
		uriText = new Text(container, SWT.NONE);
		data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = SWT.FILL;
		uriText.addModifyListener(new UpdateModifyListener(this));
		uriText.setLayoutData(data);

		multiplicityLabel = new Label(container, SWT.NONE);
		multiplicityLabel.setText("Multiplicity");
		
		multiplicitySpinner = new Spinner(container, SWT.NONE);
		multiplicitySpinner.setMinimum(0);
		multiplicitySpinner.setMaximum(Integer.MAX_VALUE);
		data = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		data.horizontalSpan = 2;
		multiplicitySpinner.setLayoutData(data);

		headerViewer = new HeaderTableViewer(container, getNestedCommands());
		headerViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = WizardUtils.getTableViewerGridData();
		data.horizontalSpan = 2;
		headerViewer.getTable().setLayoutData(data);
		
		headerControl = new TableViewerControl(container, true, SWT.NONE);
		headerControl.getAddButton().addSelectionListener(
				new AddHeaderSelectionAdapter(getNestedCommands(),
						headerViewer, message));
		headerControl.getDeleteButton().addSelectionListener(
				new DeleteHeaderSelectionAdapter(getNestedCommands(),
						headerViewer, message));
		headerControl.getEditButton().addSelectionListener(new AbstractEditCommandSelectionAdapter(getNestedCommands(), headerViewer) {
			
			@Override
			protected AbstractPerfCakeEditWizard createWizard(
					IStructuredSelection selection) {
				return new HeaderEditWizard((HeaderModel) selection.getFirstElement());
			}
		});
		
		propertyViewer = new PropertyTableViewer(container, getNestedCommands());
		propertyViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = WizardUtils.getTableViewerGridData();
		data.horizontalSpan = 2;
		propertyViewer.getTable().setLayoutData(data);
		
		propertyControl = new TableViewerControl(container, true, SWT.NONE);
		propertyControl.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getNestedCommands(),
						propertyViewer, message));
		propertyControl.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(getNestedCommands(),
						propertyViewer, message));
		propertyControl.getEditButton().addSelectionListener(
				new EditPropertySelectionAdapter(getNestedCommands(), propertyViewer));
		
		refViewer = new ValidatorRefTableViewer(container, getNestedCommands());
		if (validators != null){
			refViewer.setAlternativeValidators(validators);
		}
		refViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = WizardUtils.getTableViewerGridData();
		data.horizontalSpan = 2;
		refViewer.getTable().setLayoutData(data);
		
		//TODO: figure out ValidatorRef control
		refControl = new TableViewerControl(container, false, SWT.NONE);
		refControl.getAddButton().setText("Attach");
		AttachValidatorSelectionAdapter attachAdapter = new AttachValidatorSelectionAdapter(getNestedCommands(),
				refViewer, message);
		attachAdapter.setValidators(validators);
		refControl.getAddButton().addSelectionListener(attachAdapter);
		refControl.getDeleteButton().setText("Detach");
		refControl.getDeleteButton().addSelectionListener(
				new DetachValidatorSelectionAdapter(getNestedCommands(),
						refViewer, message));
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void updateControls() {
		if ("".equals(uriText.getText())){
			setDescription("Fill in message URI.");
			setPageComplete(false);
			return;
		}
		setPageComplete(true);
		setDescription("Complete!");
		super.updateControls();
	}


	@Override
	protected void fillCurrentValues() {
		if (message.getMessage().getUri() != null){
			uriText.setText(message.getMessage().getUri());
		}
		if (message.getMessage().getMultiplicity() != null){
			multiplicitySpinner.setSelection(
					Integer.valueOf(message.getMessage().getMultiplicity()));
		} else{
			multiplicitySpinner.setSelection(1);
		}
		if (properties != null)
			propertyViewer.setInput(properties);
		if (headers != null)
			headerViewer.setInput(headers);
		if (refs != null)
			refViewer.setInput(refs);
		super.fillCurrentValues();
	}

	@Override
	protected void fillDefaultValues() {
		multiplicitySpinner.setSelection(1);
		super.fillDefaultValues();
	}
	
	public String getUri(){
		return uriText.getText();
	}
	
	public int getMultiplicity(){
		return multiplicitySpinner.getSelection();
	}

	public PropertyTableViewer getPropertyViewer() {
		return propertyViewer;
	}

	public HeaderTableViewer getHeaderViewer() {
		return headerViewer;
	}

	public ValidatorRefTableViewer getRefViewer() {
		return refViewer;
	}
	
	/**
	 * Initializes list of validators to which new validators (created by wizard) will be added.
	 * These validator are not currently in scenario, so it will be stored in this list.
	 * @param validators non null list of validators (may be empty)
	 */
	public void setValidators(List<ValidatorModel> validators) {
		this.validators = validators;
	}
}
