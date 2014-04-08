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
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.logging.Logger;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.model.ValidatorRefModel;
import org.perfclipse.ui.Activator;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.figures.IAnchorFigure;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.ValidatorEditPolicy;
import org.perfclipse.ui.gef.policies.ValidatorGraphicalNodeEditPolicy;
import org.perfclipse.ui.gef.policies.directedit.ValidatorDirectEditPolicy;
import org.perfclipse.ui.preferences.PreferencesConstants;
import org.perfclipse.ui.wizards.ValidatorEditWizard;

public class ValidatorEditPart extends AbstractPerfCakeNodeEditPart implements
PropertyChangeListener, NodeEditPart {

	static final Logger log = Activator.getDefault().getLogger();

	protected DirectEditManager manager;

	public ValidatorEditPart(ValidatorModel validatorModel){
		setModel(validatorModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getValidatorModel().addPropertyChangeListener(this);
		}
		super.activate();
	}
	
	@Override
	public void deactivate() {
		getValidatorModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public ValidatorModel getValidatorModel(){
		return (ValidatorModel) getModel();
	}
	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		Color fg = colorUtils.getColor(PreferencesConstants.VALIDATOR_COLOR_FOREGROUND);
		Color bg = colorUtils.getColor(PreferencesConstants.VALIDATOR_COLOR_BACKGROUND);
		LabeledRoundedRectangle figure = new LabeledRoundedRectangle(getText(), fg, bg);
		return figure;
	}

	@Override
	protected String getText() {
		return "(" + getValidatorModel().getValidator().getId() + ") "
				+ getValidatorModel().getValidator().getClazz() ;
	}

	@Override
	public void performRequest(Request request){
		if (request.getType() == RequestConstants.REQ_OPEN ||
				request.getType() == RequestConstants.REQ_DIRECT_EDIT)
		{
			ValidatorEditWizard wizard = new ValidatorEditWizard(getValidatorModel());
			if (Utils.showWizardDialog(wizard) == Window.OK){
				if (! wizard.getCommand().isEmpty()){
					getViewer().getEditDomain().getCommandStack().execute(wizard.getCommand());
				}
			}
			
		}
	}
	
	@Override
	protected void createEditPolicies() {
		ValidationModel validation = (ValidationModel) getParent().getModel();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new ValidatorDirectEditPolicy(getValidatorModel(), (ILabeledFigure) getFigure()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ValidatorEditPolicy(validation, getValidatorModel()));
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ValidatorGraphicalNodeEditPolicy(getValidatorModel()));

	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(ValidatorModel.PROPERTY_CLASS) ||
				e.getPropertyName().equals(ValidatorModel.PROPERTY_ID)){
			refreshVisuals();
		}
	}
	
	@Override
	protected List<?> getModelSourceConnections() {
		// TODO Auto-generated method stub
		return super.getModelSourceConnections();
	}

	@Override
	protected List<?> getModelTargetConnections() {
		ModelMapper mapper = getValidatorModel().getMapper();
		MessagesModel messages = mapper.getMessagesModel();
		
		List<ValidatorRefModel> references = new ArrayList<>();
		
		if (messages == null || messages.getMessages() == null)
			return Collections.EMPTY_LIST;

		for (Message m : messages.getMessages().getMessage()){
			
			for (ValidatorRef ref : m.getValidatorRef()){
				if (ref.getId().equals(getValidatorModel().getValidator().getId())){
					references.add((ValidatorRefModel) mapper.getModelContainer(ref));
				}
			}
		}

		return references;
	}
	
	public void refreshValidatorConnections(){
		refreshTargetConnections();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return ((IAnchorFigure) getFigure()).getConnectionAnchor();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return ((IAnchorFigure) getFigure()).getConnectionAnchor();
	}

}
