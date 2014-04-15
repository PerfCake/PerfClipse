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

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.swt.events.AddMessageSelectionAdapter;
import org.perfclipse.ui.swt.events.DeleteMessageSelectionAdapter;
import org.perfclipse.ui.swt.events.EditMessageSelectionAdapter;
import org.perfclipse.ui.swt.jface.MessagesTableViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class MessagesPage extends AbstractPerfCakePage {


	public static final String MESSAGES_PAGE_NAME = "Messages";
	private MessagesModel messagesModel;
	private List<MessageModel> messagesList;
	private Composite container;
	private TableViewer messagesViewer;
	private TableViewerControl messagesViewerControls;
	
	//validators added by user, which are not in scenario.
	private List<ValidatorModel> validators;
	

	//scenario file
	private IFile scenarioFile;
	private AddMessageSelectionAdapter addMessageAdapter;
	private EditMessageSelectionAdapter editMessageAdapter;
	private DeleteMessageSelectionAdapter deleteMessageAdapter;

	public MessagesPage(IFile scenarioFile){
		this(MESSAGES_PAGE_NAME, false, scenarioFile);
	}
	
	public MessagesPage(MessagesModel messagesModel, IFile scenarioFile){
		this(MESSAGES_PAGE_NAME, true, scenarioFile);
		this.messagesModel = messagesModel;

		if (messagesModel.getMessages() == null){
			setEditMode(false);
			return;
		}
		ModelMapper mapper = messagesModel.getMapper();
		messagesList = new ArrayList<>(messagesModel.getMessages().getMessage().size());
		
		for (Message m : messagesModel.getMessages().getMessage()){
			messagesList.add((MessageModel) mapper.getModelContainer(m));
		}
	}
	
	private MessagesPage(String pageName, boolean edit, IFile scenarioFile){
		super(pageName, edit);
		this.scenarioFile = scenarioFile;
	}

	@Override
	public void createControl(Composite parent) {
		setTitle("Messages section");
		setDescription("Add messages.");
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		GridData data;
		
		messagesViewer = new MessagesTableViewer(container, getEditingSupportCommands());

		messagesViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = Utils.getTableViewerGridData();
		data.grabExcessHorizontalSpace = true;
		messagesViewer.getTable().setLayoutData(data);

		messagesViewerControls = new TableViewerControl(container, true, SWT.NONE);
		addMessageAdapter = new AddMessageSelectionAdapter(getEditingSupportCommands(),
				messagesViewer, messagesModel, scenarioFile);
		addMessageAdapter.setValidators(validators);
		messagesViewerControls.getAddButton().addSelectionListener(addMessageAdapter); 

		editMessageAdapter = new EditMessageSelectionAdapter(getEditingSupportCommands(),
				messagesViewer);
		editMessageAdapter.setValidators(validators);
		messagesViewerControls.getEditButton().addSelectionListener(editMessageAdapter);
		deleteMessageAdapter = new DeleteMessageSelectionAdapter(getEditingSupportCommands(),
				messagesViewer, messagesModel, scenarioFile); 
		messagesViewerControls.getDeleteButton().addSelectionListener(deleteMessageAdapter);
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
		if (messagesList != null)
			messagesViewer.setInput(messagesList);
		super.fillCurrentValues();
	}

	public TableViewer getMessagesViewer() {
		return messagesViewer;
	}

	/**
	 * Initializes list of validators to which new validators (created by wizard) will be added.
	 * These validator are not currently in scenario, so it will be stored in this list.
	 * @param validators non null list of validators (may be empty)
	 */
	public void setValidators(List<ValidatorModel> validators) {
		this.validators = validators;
	}

	public IFile getScenarioFile() {
		return scenarioFile;
	}

	public void setScenarioFile(IFile scenarioFile) {
		this.scenarioFile = scenarioFile;
		//pass scenariofile to selection adapters
		addMessageAdapter.setScenarioFile(scenarioFile);
	}
	
	
}