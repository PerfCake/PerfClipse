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
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.jface.window.Window;
import org.perfcake.model.Property;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.SenderModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.PropertyListEditPolicy;
import org.perfclipse.ui.gef.policies.SenderEditPolicy;
import org.perfclipse.ui.gef.policies.directedit.SenderDirectEditPolicy;
import org.perfclipse.ui.wizards.SenderEditWizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener {

	static final Logger log = LoggerFactory.getLogger(SenderEditPart.class);

	@Override
	public void activate() {
		if (!isActive()){
			getSenderModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getSenderModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public SenderEditPart(SenderModel senderModel){
		setModel(senderModel);
	}

	public SenderModel getSenderModel(){
		return (SenderModel) getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		TwoPartRectangle figure = new TwoPartRectangle(getText(), 
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
		return figure;
	}

	@Override
	protected String getText() {
		return getSenderModel().getSender().getClazz();
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_OPEN ||
				req.getType() == RequestConstants.REQ_DIRECT_EDIT){
			SenderEditWizard wizard = new SenderEditWizard(getSenderModel());
			if (Utils.showWizardDialog(wizard) == Window.OK){
				CompoundCommand command = wizard.getCommand();
				if (!command.isEmpty()){
					getViewer().getEditDomain().getCommandStack().execute(command);
				}
			}
		}
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new SenderDirectEditPolicy(getSenderModel(), (ILabeledFigure) getFigure()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new SenderEditPolicy(getSenderModel()));
		
		// not used for any actions but only for making selection visible
		NonResizableEditPolicy policy = new NonResizableEditPolicy();
		policy.setDragAllowed(false);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, policy);
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new PropertyListEditPolicy(getSenderModel()));
	}
	

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();

		ModelMapper mapper = getSenderModel().getMapper();

		if (getSenderModel().getSender().getProperty() != null){
			for (Property p : getSenderModel().getSender().getProperty()){
				modelChildren.add(mapper.getModelContainer(p));
			}
		}
		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(SenderModel.PROPERTY_CLASS)){
			refreshVisuals();
		}
		if (evt.getPropertyName().equals(SenderModel.PROPERTY_PROPERTIES)){
			refreshChildren();
		}
	}

}
