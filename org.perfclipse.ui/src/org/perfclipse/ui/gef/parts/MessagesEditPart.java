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

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.MessagesListEditPolicy;

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
		TwoPartRectangle figure = new TwoPartRectangle(getText(), getDefaultSize(),
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
//		setFigureDefaultSize(figure.getPreferredSize().getCopy());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		ScenarioModel scenarioModel = ((ScenarioEditPart) getParent()).getScenarioModel();
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new MessagesListEditPolicy(getMessagesModel(), scenarioModel));
	}
	
	
	@Override
	protected String getText(){
		return MESSAGES_SECTION_LABEL;
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
			//if message is deleted
			if (e.getNewValue() == null && e.getOldValue() instanceof Message){
				List<EditPart> toDelete = new ArrayList<>();
				for (Object children : getChildren()){
					EditPart child = (EditPart) children;
					MessageModel model = (MessageModel) child.getModel();
					if (model.getMessage() == e.getOldValue()){
						toDelete.add(child);
					}
				}
				
				for (EditPart part : toDelete){
					removeChild(part);
				}
			}
		}
		
	}
}
