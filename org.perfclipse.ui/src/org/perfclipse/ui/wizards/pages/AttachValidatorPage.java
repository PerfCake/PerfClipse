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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorRefModel;

/**
 * @author Jakub Knetl
 *
 */
public class AttachValidatorPage extends ValidationPage {

	/**
	 * 
	 */
	public AttachValidatorPage() {
	}

	/**
	 * @param validation
	 */
	public AttachValidatorPage(ValidationModel validation) {
		super(validation);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		setTitle("Attach validator");
		setDescription("Select validator to attach");
		setPageComplete(false);
		getValidatorViewer().addSelectionChangedListener(new UpdateSelectionChangeListener(this));
	}

	@Override
	protected void updateControls() {
		IStructuredSelection selection;
		if (! (getValidatorViewer().getSelection() instanceof IStructuredSelection))
			setPageComplete(false);

		selection = (IStructuredSelection) getValidatorViewer().getSelection();
			
		if (!(selection.getFirstElement() instanceof ValidatorRefModel))
			setPageComplete(false);

		setPageComplete(true);
	}

}
