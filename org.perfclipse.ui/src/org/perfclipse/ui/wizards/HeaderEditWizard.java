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

import org.perfcake.model.Header;
import org.perfclipse.model.HeaderModel;
import org.perfclipse.ui.gef.commands.EditHeaderNameCommand;
import org.perfclipse.ui.gef.commands.EditHeaderValueCommand;
import org.perfclipse.ui.wizards.pages.HeaderPage;

/**
 * @author Jakub Knetl
 *
 */
public class HeaderEditWizard extends AbstractPerfCakeEditWizard {
	
	private HeaderModel header;
	private HeaderPage headerPage;

	/**
	 * @param header
	 */
	public HeaderEditWizard(HeaderModel header) {
		super("Edit header");
		this.header = header;
	}

	@Override
	public boolean performFinish() {
		Header h = header.getHeader();
		if (h.getName() == null || 
				!h.getName().equals(headerPage.getNameText().getText()))
			getCommand().add(new EditHeaderNameCommand(header, headerPage.getNameText().getText()));
		if (h.getValue() == null ||
				!h.getValue().equals(headerPage.getValueText().getText()))
			getCommand().add(new EditHeaderValueCommand(header, headerPage.getValueText().getText()));

		return super.performFinish();
	}

	@Override
	public void addPages() {
		headerPage = new HeaderPage(header);
		addPage(headerPage);
		super.addPages();
	}
	
	
	
	

}
