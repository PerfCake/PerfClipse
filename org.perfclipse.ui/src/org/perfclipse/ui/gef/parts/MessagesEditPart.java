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
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.ui.gef.commands.AddMessageCommand;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;

public class MessagesEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener {

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
		
		TwoPartRectangle figure = new TwoPartRectangle("Messeges section", getDefaultSize());
//		setFigureDefaultSize(figure.getPreferredSize().getCopy());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new OrderedLayoutEditPolicy() {
			
			@Override
			protected Command getCreateCommand(CreateRequest request) {
				Object type = request.getNewObjectType();
				if (type == Message.class){
					Message message = (Scenario.Messages.Message) request.getNewObject();
					if (getMessagesModel().getMessages() == null){
						getMessagesModel().createMessages();
						((ScenarioEditPart) getParent()).getScenarioModel().getScenario().setMessages(getMessagesModel().getMessages());
					}
					return new AddMessageCommand(message, getMessagesModel());
				}
				return null;
			}
			
			@Override
			protected EditPart getInsertionReference(Request request) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected Command createMoveChildCommand(EditPart child, EditPart after) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected Command createAddCommand(EditPart child, EditPart after) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		if (getMessagesModel().getMessages() != null){
			if (getMessagesModel().getMessages().getMessage() != null){
				for (Message m : getMessagesModel().getMessages().getMessage()){
					modelChildren.add(new MessageModel(m));
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
				MessageModel messageModel = new MessageModel((Message) e.getNewValue());
				addChild(new MessageEditPart(messageModel), getChildren().size());
			}
		}
		
	}
}
