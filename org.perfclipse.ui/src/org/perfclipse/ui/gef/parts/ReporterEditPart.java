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
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.DestinationListEditPolicy;

public class ReporterEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener {

	
	public ReporterEditPart(ReporterModel reporterModel){
		setModel(reporterModel);
	}
	
	
	@Override
	public void activate() {
		if (!isActive()){
			getReporterModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getReporterModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	
	public ReporterModel getReporterModel(){
		return (ReporterModel) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		Dimension d = ((AbstractGraphicalEditPart) getParent()).getFigure().getClientArea().getSize().getCopy();
		d.setHeight(150);
		ColorUtils colorUtils = ColorUtils.getInstance();
		TwoPartRectangle figure = new TwoPartRectangle(getText(), d,
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DestinationListEditPolicy(getReporterModel()));

	}
	
	@Override
	protected String getText() {
		return getReporterModel().getReporter().getClazz();
	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		if (getReporterModel().getReporter().getDestination() != null &&
				getReporterModel().getReporter().getDestination() != null)
		{
			for (Destination d: getReporterModel().getReporter().getDestination()){
				modelChildren.add(new DestinationModel(d));
			}
		}
		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ReporterModel.PROPERTY_DESTINATIONS)){
			if (evt.getOldValue() == null && evt.getNewValue() instanceof Destination){
				DestinationModel destinationModel = new DestinationModel((Destination) evt.getNewValue());
				addChild(new DestinationEditPart(destinationModel), getChildren().size());
			}
		}
		
	}

}
