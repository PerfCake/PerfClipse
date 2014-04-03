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
import org.eclipse.draw2d.Label;
import org.perfclipse.model.RunModel;
import org.perfclipse.ui.gef.figures.EditableLabel;

public class RunEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener {

	private Label label;
	
	@Override
	public void activate() {
		if (!isActive()){
			getRunModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getRunModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public RunEditPart(RunModel runModel){
		setModel(runModel);
	}
	
	public RunModel getRunModel(){
		return (RunModel) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		label = new EditableLabel("Run", null, null); 
		label.setText(getText());
		return label;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected String getText() {
		return getRunModel().getRun().getType() + " : " + getRunModel().getRun().getValue();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(RunModel.PROPERTY_TYPE) ||
				evt.getPropertyName().equals(RunModel.PROPERTY_VALUE)){
			refreshVisuals();
		}
	}

}
