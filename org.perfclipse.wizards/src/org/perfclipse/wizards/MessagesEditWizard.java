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

package org.perfclipse.wizards;

import org.eclipse.core.resources.IFile;
import org.perfclipse.core.model.MessagesModel;
import org.perfclipse.wizards.pages.MessagesPage;

/**
 * @author Jakub Knetl
 *
 */
public class MessagesEditWizard extends AbstractPerfCakeEditWizard{

	private MessagesPage messagesPage;
	private MessagesModel messagesModel;
	
	//scenario file
	private IFile scenarioFile;

	/**
	 * @param messagesModel
	 */
	public MessagesEditWizard(MessagesModel messagesModel, IFile scenarioFile) {
		super("Edit Messages");
		this.messagesModel = messagesModel;
		this.scenarioFile = scenarioFile;
	}
	@Override
	public boolean performFinish() {
		
		return super.performFinish();
	}
	@Override
	public void addPages() {
		messagesPage = new MessagesPage(messagesModel, scenarioFile);
		addPage(messagesPage);
		super.addPages();
	}

}
