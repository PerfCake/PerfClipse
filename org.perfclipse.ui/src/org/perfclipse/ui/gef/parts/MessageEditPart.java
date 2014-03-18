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
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.ui.gef.directedit.LabelCellEditorLocator;
import org.perfclipse.ui.gef.directedit.LabelDirectEditManager;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.MessageEditPolicy;
import org.perfclipse.ui.gef.policies.directedit.MessageDirectEditPolicy;

public class MessageEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener{

	protected DirectEditManager manager;

	public MessageEditPart(MessageModel modelMessage, 
			ModelMapper mapper){
		super(mapper);
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
		LabeledRoundedRectangle figure = new LabeledRoundedRectangle(getText(),
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));

		return figure;
	}
	
	@Override
	public void performRequest(Request request){
		if (request.getType() == RequestConstants.REQ_OPEN ||
				request.getType() == RequestConstants.REQ_DIRECT_EDIT)
		{
			if (manager == null){
				manager = new LabelDirectEditManager(this,
						TextCellEditor.class,
						new LabelCellEditorLocator(((LabeledRoundedRectangle) getFigure()).getLabel()));
			}
			manager.show();
			
		}
	}

	@Override
	protected void createEditPolicies() {
		MessagesModel messages = (MessagesModel) getParent().getModel();

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new MessageDirectEditPolicy(getMessageModel(), (ILabeledFigure) getFigure()));
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
	}
}
