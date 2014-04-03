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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.DestinationListEditPolicy;
import org.perfclipse.ui.gef.policies.ReporterEditPolicy;
import org.perfclipse.ui.gef.policies.directedit.RenameReporterDirectEditPolicy;
import org.perfclipse.ui.preferences.PreferencesConstants;
import org.perfclipse.ui.wizards.ReporterEditWizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReporterEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener {
	
	static final Logger log = LoggerFactory.getLogger(ReporterEditPart.class);

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
		Color fg = colorUtils.getColor(PreferencesConstants.REPORTER_COLOR_FOREGROUND);
		Color bg = colorUtils.getColor(PreferencesConstants.REPORTER_COLOR_BACKGROUND);
		TwoPartRectangle figure = new TwoPartRectangle(getText(), fg, bg);
		return figure;
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_OPEN ||
				req.getType() == RequestConstants.REQ_DIRECT_EDIT){
			ReporterEditWizard wizard = new ReporterEditWizard(getReporterModel());
			if (Utils.showWizardDialog(wizard) == Window.OK){
				if (!wizard.getCommand().isEmpty())
					getViewer().getEditDomain().getCommandStack().execute(wizard.getCommand());
			}
		}
	}
	
	@Override
	protected void createEditPolicies() {
		ReportingModel reporting = (ReportingModel) getParent().getModel();
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new DestinationListEditPolicy(getReporterModel()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ReporterEditPolicy(reporting, getReporterModel()));
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new RenameReporterDirectEditPolicy(getReporterModel(), (ILabeledFigure) getFigure()));

	}
	
	@Override
	protected String getText() {
		return getReporterModel().getReporter().getClazz();
	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		ModelMapper mapper = getReporterModel().getMapper();
		if (getReporterModel().getReporter().getDestination() != null &&
				getReporterModel().getReporter().getDestination() != null)
		{
			for (Destination d: getReporterModel().getReporter().getDestination()){
				modelChildren.add(mapper.getModelContainer(d));
			}
		}
		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ModelMapper mapper = getReporterModel().getMapper();
		if (evt.getPropertyName().equals(ReporterModel.PROPERTY_DESTINATIONS)){
			if (evt.getOldValue() == null && evt.getNewValue() instanceof Destination){
				Destination d = (Destination) evt.getNewValue();
				DestinationModel destinationModel = (DestinationModel) mapper.getModelContainer(d);
				int index = getReporterModel().getReporter().getDestination().indexOf(destinationModel.getDestination());
				addChild(new DestinationEditPart(destinationModel), index);
			}
			if (evt.getNewValue() == null && evt.getOldValue() instanceof Destination){
				List<EditPart> toDelete = new ArrayList<>();
				for (Object child : getChildren()){
					EditPart part = (EditPart) child;
					DestinationModel model = (DestinationModel) part.getModel();
					if (model.getDestination() == evt.getOldValue()){
						toDelete.add(part);
					}
				}
				
				for (EditPart part: toDelete){
					removeChild(part);
				}
			}
		}
		
		if (evt.getPropertyName().equals(ReporterModel.PROPERTY_CLASS)){
			refreshVisuals();
		}
		
	}

}
