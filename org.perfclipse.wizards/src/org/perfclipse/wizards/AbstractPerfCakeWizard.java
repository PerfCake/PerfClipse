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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.perfclipse.wizards.swt.events.WizardListener;

/**
 * Abstract PerfCake wizard has support for listening changes using {@link WizardListener}
 * @author Jakub Knetl
 *
 */
public abstract class AbstractPerfCakeWizard extends Wizard{

	/**
	 * List of wizard listeners.
	 */
	protected List<WizardListener> listeners = new ArrayList<>();
	
	/**
	 * Adds listener to the wizard
	 * @param listener
	 */
	public void addWizardListener(WizardListener listener){
		listeners.add(listener);
	}
	
	/**
	 * Remove listener from the wizard;
	 * @param listener
	 */
	public void removeWizardListener(WizardListener listener){
		listeners.remove(listener);
	}
	
	public void notifyWizardListeners(){
		for (WizardListener l : listeners){
			l.performAction();
		}
	}

}
