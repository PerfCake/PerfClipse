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

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;
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


	private static final String MESSAGES_PAGE_NAME = "Messages";
	private MessagesModel messagesModel;
	private List<MessageModel> messagesList;
	private Composite container;
	private TableViewer messagesViewer;
	private TableViewerControl messagesViewerControls;

	public MessagesPage(){
		super(MESSAGES_PAGE_NAME, false);
	}
	
	public MessagesPage(MessagesModel messagesModel){
		super(MESSAGES_PAGE_NAME, true);
		this.messagesModel = messagesModel;
		ModelMapper mapper = messagesModel.getMapper();
		messagesList = new ArrayList<>(messagesModel.getMessages().getMessage().size());
		
		for (Message m : messagesModel.getMessages().getMessage()){
			messagesList.add((MessageModel) mapper.getModelContainer(m));
		}
	}
	
	public MessagesPage(String pageName, boolean edit){
		super(pageName, edit);
		setTitle("Messages section");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		GridData data;
		
		messagesViewer = new MessagesTableViewer(container, getEditingSupportCommands());
		messagesViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		messagesViewer.getTable().setLayoutData(data);
		
		messagesViewerControls = new TableViewerControl(container, true, SWT.NONE);
		messagesViewerControls.getAddButton().addSelectionListener(
				new AddMessageSelectionAdapter(getEditingSupportCommands(), messagesViewer, messagesModel));
		messagesViewerControls.getEditButton().addSelectionListener(
				new EditMessageSelectionAdapter(getEditingSupportCommands(), messagesViewer));
		messagesViewerControls.getDeleteButton().addSelectionListener(
				new DeleteMessageSelectionAdapter(getEditingSupportCommands(), messagesViewer, messagesModel));
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
		messagesViewer.setInput(messagesList);
		super.fillCurrentValues();
	}

	public TableViewer getMessagesViewer() {
		return messagesViewer;
	}
}