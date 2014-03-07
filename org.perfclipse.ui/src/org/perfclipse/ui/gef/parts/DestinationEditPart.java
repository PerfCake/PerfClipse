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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.DeleteDestionationEditPolicy;

public class DestinationEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener {

	public DestinationEditPart(DestinationModel destinationModel){
		setModel(destinationModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getDestinationModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getDestinationModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	public DestinationModel getDestinationModel(){
		return (DestinationModel) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		LabeledRoundedRectangle figure = new LabeledRoundedRectangle(getText(),
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
//		figure.setPreferredSize(EMPTY_WIDHT, EMPTY_HEIGHT);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		ReporterModel reporter = (ReporterModel) getParent().getModel();
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new DeleteDestionationEditPolicy(reporter, getDestinationModel()));

	}

	@Override
	protected String getText() {
		// TODO Auto-generated method stub
		return getDestinationModel().getDestination().getClazz();
	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

}
