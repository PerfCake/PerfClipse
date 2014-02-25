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
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.commands.RenameValidatorCommand;
import org.perfclipse.ui.gef.directedit.LabelCellEditorLocator;
import org.perfclipse.ui.gef.directedit.LabelDirectEditManager;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;

public class ValidatorEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener {

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
		LabeledRoundedRectangle figure = new LabeledRoundedRectangle(getText());
		return figure;
	}

	@Override
	protected String getText() {
		return getValidatorModel().getValidator().getClazz();
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
		// TODO Auto-generated method stub
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {
			
			@Override
			protected void showCurrentEditValue(DirectEditRequest request) {
				((LabeledRoundedRectangle) getFigure()).getLabel().setText((String) request.getCellEditor().getValue());
			}
			
			@Override
			protected Command getDirectEditCommand(DirectEditRequest request) {
				return new RenameValidatorCommand(getValidatorModel(), (String) request.getCellEditor().getValue());
			}
		});

	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(ValidatorModel.PROPERTY_CLASS)){
			refreshVisuals();
		}
	}

}
