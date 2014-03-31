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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.jface.window.Window;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.MessagesEditPolicy;
import org.perfclipse.ui.gef.policies.MessagesListEditPolicy;
import org.perfclipse.ui.wizards.MessagesEditWizard;

public class MessagesEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener {

	private static final String MESSAGES_SECTION_LABEL = "Messages";

	public MessagesEditPart(MessagesModel messagesModel){
		setModel(messagesModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getMessagesModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getMessagesModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public MessagesModel getMessagesModel(){
		return (MessagesModel) getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		
		ColorUtils colorUtils = ColorUtils.getInstance();
		TwoPartRectangle figure = new TwoPartRectangle(getText(),
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
		return figure;
	}
	
	

	@Override
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_OPEN ||
				request.getType() == RequestConstants.REQ_DIRECT_EDIT)
		{
			MessagesEditWizard wizard = new MessagesEditWizard(getMessagesModel());
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
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new MessagesListEditPolicy(getMessagesModel()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new MessagesEditPolicy(getMessagesModel()));
		
		// not used for any actions but only for making selection visible
		NonResizableEditPolicy policy = new NonResizableEditPolicy();
		policy.setDragAllowed(false);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, policy);
	}
	
	
	@Override
	protected String getText(){
		return MESSAGES_SECTION_LABEL;
	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		ModelMapper mapper = getMessagesModel().getMapper();
		if (getMessagesModel().getMessages() != null){
			if (getMessagesModel().getMessages().getMessage() != null){
				for (Message m : getMessagesModel().getMessages().getMessage()){
					modelChildren.add(mapper.getModelContainer(m));
				}
			}
		}
		return modelChildren;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(MessagesModel.PROPERTY_MESSAGE)){
			
			//if message is added
			if(e.getOldValue() == null && e.getNewValue() instanceof Message){
				Message m = (Message) e.getNewValue();
				MessageModel messageModel = (MessageModel) getMessagesModel().getMapper().getModelContainer(m);
				int index = getMessagesModel().getMessages().getMessage().indexOf(messageModel.getMessage());
				addChild(new MessageEditPart(messageModel), index);
			}
			//if message is deleted
			if (e.getNewValue() == null && e.getOldValue() instanceof Message){
				List<EditPart> toDelete = new ArrayList<>();
				for (Object child : getChildren()){
					EditPart part = (EditPart) child;
					MessageModel model = (MessageModel) part.getModel();
					if (model.getMessage() == e.getOldValue()){
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
