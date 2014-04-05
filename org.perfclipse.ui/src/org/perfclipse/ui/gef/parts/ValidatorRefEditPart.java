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

package org.perfclipse.ui.gef.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.swt.SWT;
import org.perfclipse.model.ValidatorRefModel;

/**
 * Edit part which handles connections in the model.
 * 
 * @author Jakub Knetl
 *
 */
public class ValidatorRefEditPart extends AbstractConnectionEditPart implements PropertyChangeListener {


	public ValidatorRefEditPart(ValidatorRefModel model) {
		setModel(model);
	}
	@Override
	public void activate() {
		if (!isActive()){
			getValidatorRefModel().addPropertyChangeListener(this);
		}
		super.activate();
	}
	
	@Override
	public void deactivate() {
		getValidatorRefModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public ValidatorRefModel getValidatorRefModel(){
		return (ValidatorRefModel) getModel();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		PolylineConnection connection = new PolylineConnection();
		connection.setLineStyle(SWT.LINE_DASH);

		return connection;
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ValidatorRefModel.PROPERTY_ID)){
			//TODO: just call refresh connections instead of refreshing whole subtree
			getTarget().refresh();
			getSource().refresh();
		}
		
	}
	
	
}
