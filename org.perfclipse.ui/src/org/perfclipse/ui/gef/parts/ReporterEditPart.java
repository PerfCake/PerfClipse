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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;

public class ReporterEditPart extends AbstractPerfCakeNodeEditPart {

	
	public ReporterEditPart(ReporterModel reporterModel){
		setModel(reporterModel);
	}
	
	public ReporterModel getReporterModel(){
		return (ReporterModel) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		Dimension d = ((AbstractGraphicalEditPart) getParent()).getFigure().getClientArea().getSize().getCopy();
		d.setHeight(150);
		TwoPartRectangle figure = new TwoPartRectangle(getText(), d); 
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

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

}
