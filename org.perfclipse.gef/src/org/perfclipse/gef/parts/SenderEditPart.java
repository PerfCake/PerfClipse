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
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.perfcake.model.Property;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.SenderModel;
import org.perfclipse.gef.GEFActivator;
import org.perfclipse.gef.PreferencesConstants;
import org.perfclipse.gef.figures.ILabeledFigure;
import org.perfclipse.gef.figures.TwoPartRectangle;
import org.perfclipse.gef.layout.colors.ColorUtils;
import org.perfclipse.gef.policies.PropertyListEditPolicy;
import org.perfclipse.gef.policies.SenderEditPolicy;
import org.perfclipse.gef.policies.directedit.SenderDirectEditPolicy;
import org.perfclipse.wizards.SenderEditWizard;
import org.perfclipse.wizards.WizardUtils;

public class SenderEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener {

	static final Logger log = GEFActivator.getDefault().getLogger();

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
		Color fg = colorUtils.getColor(PreferencesConstants.SENDER_COLOR_FOREGROUND);
		Color bg = colorUtils.getColor(PreferencesConstants.SENDER_COLOR_BACKGROUND);
		TwoPartRectangle figure = new TwoPartRectangle(getText(), fg, bg);
		return figure;
	}

	@Override
	protected String getText() {
		return getSenderModel().getSender().getClazz();
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_OPEN){
			SenderEditWizard wizard = new SenderEditWizard(getSenderModel());
			if (WizardUtils.showWizardDialog(wizard) == Window.OK){
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
