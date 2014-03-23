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

import org.perfclipse.model.SenderModel;
import org.perfclipse.ui.gef.commands.EditSenderTypeCommand;
import org.perfclipse.ui.wizards.pages.SenderPage;

/**
 * @author Jakub Knetl
 *
 */
public class SenderEditWizard extends AbstractPerfCakeEditWizard {

	private SenderModel sender;
	private SenderPage senderPage;
	
	public SenderEditWizard(SenderModel sender) {
		super("Edit Sender");
		this.sender = sender;
	}

	@Override
	public boolean performFinish() {

		if (!(sender.getSender().getClazz().equals(senderPage.getSenderName()))){
			command.add(new EditSenderTypeCommand(sender, senderPage.getSenderName()));
		}
		
		return super.performFinish();
	}

	@Override
	public void addPages() {
		senderPage = new SenderPage(sender);
		addPage(senderPage);
	}

}
