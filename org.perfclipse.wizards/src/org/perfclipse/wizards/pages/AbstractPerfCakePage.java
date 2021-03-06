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

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.reflect.PerfCakeComponents;
import org.perfclipse.core.reflect.PerfClipseScannerException;
import org.perfclipse.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.wizards.Activator;

/**
 * 
 * Page which support AbstractPerfCakeWizard API for managing GEF commands.
 * 
 * @author Jakub Knetl
 *
 */
public abstract class AbstractPerfCakePage extends WizardPage {
	
	static final Logger log = Activator.getDefault().getLogger();
	
	private boolean editMode;

	/**
	 * 
	 * @param pageName Page name
	 * @param edit Is page editing existing model?
	 */
	public AbstractPerfCakePage(String pageName, boolean edit) {
		super(pageName);
		this.editMode = edit;
	}

	
	protected PerfCakeComponents getPerfCakeComponents(){
		PerfCakeComponents components = null;
		try {
			components = PerfCakeComponents.getInstance();
		} catch (PerfClipseScannerException e) {
			log.error("Cannot parse PerfCake components", e);
			MessageDialog.openError(getShell(), "Cannot parse PerfCake components",
					"Automatically loaded components from PerfCake will not be available");
		}
		
		return components;
	}
	
	
	@Override
	public void createControl(Composite parent) {
		fillValues();
		updateControls();
	}

	/**
	 * Method which updates control of the wizard.
	 * It should be called from widget listeners when user change input.
	 * It could e. g. be used for marking page complete.
	 */
	protected void updateControls(){
		// do nothing
	}
	
	/**
	 * Fills default values into wizard form.
	 */
	protected void fillDefaultValues(){
		//Do nothing
	}
	
	/**
	 * Fills in values if page is in edit mode. (it means it has some model object)
	 */
	protected void fillCurrentValues(){
		//do nothing
	}
	
	/**
	 * Fils in default values or current model values depending on editMode variable.
	 * If the Page is in editMode then current model values are filled. Otherwise 
	 * default values are filled
	 */
	protected void fillValues(){
		if (isEditMode()){
			fillCurrentValues();
		} else{
			fillDefaultValues();
		}
	}
	
	/**
	 * 
	 */
	
	/**
	 * @return list of {@link AbstractPerfCakeEditWizard} nested commands
	 * or null if wizard is not instanceof AbstractPerfCakeEditWizard
	 * 
	 * @See {@link AbstractPerfCakeEditWizard#nestedCommands}
	 */
	protected List<Command> getNestedCommands(){
		if (getWizard() instanceof AbstractPerfCakeEditWizard){
			AbstractPerfCakeEditWizard wizard = (AbstractPerfCakeEditWizard) getWizard();
			return wizard.getNestedCommands();
		}
		
		return null;
	}
	
	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	protected static class UpdateSelectionAdapter extends SelectionAdapter{
		
		AbstractPerfCakePage page;
		public UpdateSelectionAdapter(AbstractPerfCakePage page){
			this.page = page;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			page.updateControls();
		}
	}
	
	protected static class UpdateSelectionChangeListener implements ISelectionChangedListener{

		AbstractPerfCakePage page;

		public UpdateSelectionChangeListener(AbstractPerfCakePage page) {
			super();
			this.page = page;
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			page.updateControls();
		}
		
	}
	
	protected static class UpdateModifyListener implements ModifyListener{

		AbstractPerfCakePage page;

		public UpdateModifyListener(AbstractPerfCakePage page) {
			super();
			this.page = page;
		}
		@Override
		public void modifyText(ModifyEvent e) {
			page.updateControls();
		}
		
	}
}
