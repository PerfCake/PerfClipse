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

import org.eclipse.swt.widgets.TableItem;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.HeaderModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ValidatorRefModel;
import org.perfclipse.ui.wizards.pages.MessagePage;


/**
 * @author Jakub Knetl
 *
 */
/**
 * @author Jakub Knetl
 *
 */
public class MessageAddWizard extends AbstractPerfCakeAddWizard {

	private MessagePage messagePage;
	private Message message;


	/**
	 * 
	 */
	public MessageAddWizard() {
		super();
		setWindowTitle("Add message");
	}

	@Override
	public boolean performFinish() {
		
		message = new ObjectFactory().createScenarioMessagesMessage();
		message.setMultiplicity(String.valueOf(messagePage.getMultiplicity()));
		message.setUri(messagePage.getUri());
		for (TableItem i : messagePage.getPropertyViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				message.getProperty().add(p.getProperty());
			}
		}
		
		for (TableItem i : messagePage.getHeaderViewer().getTable().getItems()){
			if (i.getData() instanceof HeaderModel){
				HeaderModel h = (HeaderModel) i.getData();
				message.getHeader().add(h.getHeader());
			}
		}

		for (TableItem i : messagePage.getRefViewer().getTable().getItems()){
			if (i.getData() instanceof ValidatorRefModel){
				ValidatorRefModel v = (ValidatorRefModel) i.getData();
				message.getValidatorRef().add(v.getValidatorRef());
			}
		}
		return true;
	}

	@Override
	public void addPages() {
		messagePage = new MessagePage();
		addPage(messagePage);
		super.addPages();
	}

	public Message getMessage() {
		return message;
	}
}
