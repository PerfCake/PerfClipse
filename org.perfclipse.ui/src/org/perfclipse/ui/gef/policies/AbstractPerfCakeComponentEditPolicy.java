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

package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.jface.window.Window;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Property;
import org.perfclipse.core.commands.AddPropertyCommand;
import org.perfclipse.core.model.IPropertyContainer;
import org.perfclipse.ui.actions.PerfClipseActionConstants;
import org.perfclipse.wizards.PropertyAddWizard;
import org.perfclipse.wizards.WizardUtils;

/**
 * Component edit policy which supports custom PerfClipse requests in addition to
 * inehrited actions.
 * 
 * @author Jakub Knetl
 *
 */
public class AbstractPerfCakeComponentEditPolicy extends ComponentEditPolicy {

	/**
	 * Creates command for given request.
	 * 
	 * @return command to execute for given request.
	 */
	@Override
	public Command getCommand(Request request) {
		if (request.getType().equals(PerfClipseActionConstants.REQ_SHOW_EDIT_DIALOG)){
			return createPropertiesCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_ADD_PROPERTY)){
			return createAddPropertyCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_ADD_DESTINATION)){
			return createAddDestinationCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_ADD_HEADER)){
			return createAddHeaderCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_ADD_MESSAGE)){
			return createAddMessageCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_ADD_PERIOD)){
			return createAddPeriodCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_ADD_REPORTER)){
			return createAddReporterCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_ADD_VALIDATOR)){
			return createAddValidatorCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_ATTACH_VALIDATOR)){
			return createAttachValidatorCommand(request);
		}
		if (request.getType().equals(PerfClipseActionConstants.REQ_SWITCH)){
			return createSwitchCommand(request);
		}
		return super.getCommand(request);
	}

	/**
	 * Creates switch command
	 * @param request
	 * @return command which causes switch
	 */
	protected Command createSwitchCommand(Request request) {
		return null;
	}

	/**
	 * Creates command for PerfClipse attach validator request. 
	 * Subclass may override this method. Current implementation returns null.
	 * 
	 * @param request which invoked this method. 
	 * @return Command as reaction to request.
	 */
	protected Command createAttachValidatorCommand(Request request) {
		return null;
	}

	/**
	 * Creates command for PerfClipse add validator request. 
	 * Subclass may override this method. Current implementation returns null.
	 * 
	 * @param request which invoked this method. 
	 * @return Command as reaction to request.
	 */
	protected Command createAddValidatorCommand(Request request) {
		return null;
	}

	/**
	 * Creates command for PerfClipse add reporter request. 
	 * Subclass may override this method. Current implementation returns null.
	 * 
	 * @param request which invoked this method. 
	 * @return Command as reaction to request.
	 */
	protected Command createAddReporterCommand(Request request) {
		return null;
	}

	/**
	 * Creates command for PerfClipse add period request. 
	 * Subclass may override this method. Current implementation returns null.
	 * 
	 * @param request which invoked this method. 
	 * @return Command as reaction to request.
	 */
	protected Command createAddPeriodCommand(Request request) {
		return null;
	}

	/**
	 * Creates command for PerfClipse add message request. 
	 * Subclass may override this method. Current implementation returns null.
	 * 
	 * @param request which invoked this method. 
	 * @return Command as reaction to request.
	 */
	protected Command createAddMessageCommand(Request request) {
		return null;
	}

	/**
	 * Creates command for PerfClipse add header request. 
	 * Subclass may override this method. Current implementation returns null.
	 * 
	 * @param request which invoked this method. 
	 * @return Command as reaction to request.
	 */
	protected Command createAddHeaderCommand(Request request) {
		return null;
	}

	/**
	 * Creates command for PerfClipse add destination request. 
	 * Subclass may override this method. Current implementation returns null.
	 * 
	 * @param request which invoked this method. 
	 * @return Command as reaction to request.
	 */
	protected Command createAddDestinationCommand(Request request) {
		return null;
	}

	/**
	 * Creates command for PerfClipse add property request. 
	 * Subclass may override this method. 
	 * 
	 * @param request which invoked this method. 
	 * @return Command as reaction to request.
	 */
	protected Command createAddPropertyCommand(Request request) {
		Object model = getHost().getModel();
		if (model instanceof IPropertyContainer){
			PropertyAddWizard wizard = new PropertyAddWizard();
			if (WizardUtils.showWizardDialog(wizard) != Window.OK)
				return null;
			
			Property p = new ObjectFactory().createProperty();
			p.setName(wizard.getName());
			p.setValue(wizard.getValue());
			return new AddPropertyCommand(p, (IPropertyContainer) model);
		}
		return null;
	}

	/**
	 * Subclass may override this method to create proper Command as a response
	 * to property action. 
	 * @param request 
	 * @return Command which will react to property action request
	 */
	protected Command createPropertiesCommand(Request request) {
		return null;
	}

	
}
