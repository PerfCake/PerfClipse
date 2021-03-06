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

package org.perfclipse.gef.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.core.model.MessagesModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ReportingModel;
import org.perfclipse.gef.PreferencesConstants;
import org.perfclipse.gef.figures.TwoPartRectangle;
import org.perfclipse.gef.layout.colors.ColorUtils;
import org.perfclipse.gef.policies.ReporterListEditPolicy;
import org.perfclipse.gef.policies.ReportingEditPolicy;
import org.perfclipse.wizards.ReportingEditWizard;
import org.perfclipse.wizards.WizardUtils;

public class ReportingEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener{

	private static final String REPORTING_SECTION_LABEL = "Reporting";


	public ReportingEditPart(ReportingModel reportingModel){
		setModel(reportingModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getReportingModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getReportingModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public MessagesModel getMessagesModel(){
		return (MessagesModel) getModel();
	}
	
	public ReportingModel getReportingModel(){
		return (ReportingModel) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		
		Color fg = colorUtils.getColor(PreferencesConstants.REPORTING_COLOR_FOREGROUND);
		Color bg = colorUtils.getColor(PreferencesConstants.REPORTING_COLOR_BACKGROUND);
		TwoPartRectangle figure = new TwoPartRectangle(getText(), fg, bg);

		return figure;
	}

	@Override
	protected void createEditPolicies() {

		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new ReporterListEditPolicy(getReportingModel()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ReportingEditPolicy(getReportingModel()));
		
		// not used for any actions but only for making selection visible
		NonResizableEditPolicy policy = new NonResizableEditPolicy();
		policy.setDragAllowed(false);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, policy);
	}
	
	@Override
	protected String getText() {
		return REPORTING_SECTION_LABEL;
	}
	
	

	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_OPEN){
			ReportingEditWizard wizard = new ReportingEditWizard(getReportingModel());
			if (WizardUtils.showWizardDialog(wizard) == Window.OK){
				CompoundCommand command = wizard.getCommand();
				if (!command.isEmpty()){
					getViewer().getEditDomain().getCommandStack().execute(command);
				}
			}
		}
	}

	@Override
	protected List<Object> getModelChildren(){
		ModelMapper mapper = getReportingModel().getMapper();
		List<Object> modelChildren = new ArrayList<Object>();
		if (getReportingModel().getReporting() != null ){
			if(getReportingModel().getReporting().getReporter() != null)
			{
				for (Reporter r : getReportingModel().getReporting().getReporter()){
					modelChildren.add(mapper.getModelContainer(r));
				}
			}
		}
		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ModelMapper mapper = getReportingModel().getMapper();
		if (evt.getPropertyName().equals(ReportingModel.PROPERTY_REPORTERS)){
			if (evt.getOldValue() == null && evt.getNewValue() instanceof Reporter){
				Reporter r = (Reporter) evt.getNewValue();
				ReporterModel reporterModel = (ReporterModel) mapper.getModelContainer(r);
				int index = getReportingModel().getReporting().getReporter().indexOf(reporterModel.getReporter());
				addChild(new ReporterEditPart(reporterModel), index);
			}
			if (evt.getNewValue() == null && evt.getOldValue() instanceof Reporter){
				List<EditPart> toDelete = new ArrayList<>();
				for (Object child : getChildren()){
					EditPart part = (EditPart) child;
					ReporterModel model = (ReporterModel) part.getModel();
					if (model.getReporter() == evt.getOldValue()){
						toDelete.add(part);
					}
				}

				for (EditPart part : toDelete){
					removeChild(part);
				}
			}
		}
		
	}

}
