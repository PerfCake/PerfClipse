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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.MessagesModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.core.model.ValidatorRefModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.figures.IAnchorFigure;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.MessageEditPolicy;
import org.perfclipse.ui.gef.policies.MessageGraphicalNodeEditPolicy;
import org.perfclipse.ui.gef.policies.directedit.MessageDirectEditPolicy;
import org.perfclipse.ui.preferences.PreferencesConstants;
import org.perfclipse.ui.wizards.MessageEditWizard;

public class MessageEditPart extends AbstractPerfCakeNodeEditPart 
implements PropertyChangeListener, NodeEditPart{


	public MessageEditPart(MessageModel modelMessage){
		setModel(modelMessage);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getMessageModel().addPropertyChangeListener(this);
		}
		super.activate();
	}
	
	@Override
	public void deactivate() {
		getMessageModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public MessageModel getMessageModel(){
		return (MessageModel) getModel();
	}
	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		Color fg = colorUtils.getColor(PreferencesConstants.MESSAGE_COLOR_FOREGROUND);
		Color bg = colorUtils.getColor(PreferencesConstants.MESSAGE_COLOR_BACKGROUND);
		LabeledRoundedRectangle figure = new LabeledRoundedRectangle(getText(), fg, bg);

		return figure;
	}
	
	@Override
	public void performRequest(Request request){
		if (request.getType() == RequestConstants.REQ_OPEN)
		{
			MessageEditWizard wizard = new MessageEditWizard(getMessageModel());
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
		MessagesModel messages = (MessagesModel) getParent().getModel();

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new MessageDirectEditPolicy(getMessageModel(), (ILabeledFigure) getFigure()));
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new MessageGraphicalNodeEditPolicy(getMessageModel()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new MessageEditPolicy(messages, getMessageModel()));
	}

	@Override
	protected String getText(){
		return getMessageModel().getMessage().getUri();
	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		return modelChildren;

	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(MessageModel.PROPERTY_URI)){
			refreshVisuals();
		}
		if (e.getPropertyName().equals(MessageModel.PROPERTY_VALIDATOR_REFS)){
			refreshSourceConnections();
			// refresh validator part with deleted reference
			if (e.getOldValue() != null && e.getOldValue() instanceof ValidatorRef){
				ValidatorRef oldRef = (ValidatorRef) e.getOldValue();
				refreshReferencedValidator(oldRef.getId());
			}
		}
	}
	
	/**
	 * Refreshes all connection of validator with given id
	 * @param id Id of validator to be refreshed
	 */
	protected void refreshReferencedValidator(String id){
		ModelMapper mapper = getMessageModel().getMapper();
		if (mapper.getValidation().getValidation() == null)
			return;
		for (Validator v : mapper.getValidation().getValidation().getValidator()){
			//validator v is connected to this message
			if (id.equals(v.getId())){
				ValidatorModel validatorModel = (ValidatorModel) mapper.getModelContainer(v);
				ValidatorEditPart validatorPart = (ValidatorEditPart) getViewer().getEditPartRegistry().get(validatorModel);
				
				//if validator edit part exists.
				if (validatorPart != null)
					validatorPart.refreshValidatorConnections();
			}
		}
	}

	@Override
	protected void refreshSourceConnections() {
		super.refreshSourceConnections();
		
		//refresh target connections 
		for (ValidatorRef ref : getMessageModel().getMessage().getValidatorRef()){
			refreshReferencedValidator(ref.getId());
		}
		
	}

	@Override
	protected List<?> getModelSourceConnections() {
		List<ValidatorRefModel> connections = new ArrayList<>();
		ModelMapper mapper = getMessageModel().getMapper();
		for (ValidatorRef ref : getMessageModel().getMessage().getValidatorRef()){
			connections.add((ValidatorRefModel) mapper.getModelContainer(ref));
		}
		
		return connections;
	}

	@Override
	protected List<?> getModelTargetConnections() {
		return super.getModelTargetConnections();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return ((IAnchorFigure) getFigure()).getConnectionAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return ((IAnchorFigure) getFigure()).getConnectionAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
}
