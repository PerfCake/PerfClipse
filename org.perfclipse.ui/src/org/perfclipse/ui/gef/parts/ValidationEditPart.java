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
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.ValidatorListEditPolicy;

public class ValidationEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener {

	private static final String VALIDATION_SECTION_LABEL = "Validation section";

	public ValidationEditPart(ValidationModel validationModel){
		setModel(validationModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getValidationModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getValidationModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public ValidationModel getValidationModel(){
		return (ValidationModel) getModel();
	}
	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		TwoPartRectangle figure = new TwoPartRectangle(getText(), getDefaultSize(),
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
//		figure.setPreferredSize(EMPTY_WIDTH, EMPTY_HEIGHT);
		return figure;
	}

	@Override
	protected String getText() {
		return VALIDATION_SECTION_LABEL;
	}

	@Override
	protected void createEditPolicies() {
		ScenarioModel scenarioModel = ((ScenarioEditPart) getParent()).getScenarioModel();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ValidatorListEditPolicy(getValidationModel(), scenarioModel));


	}
	
	@Override
	protected List<Object> getModelChildren(){
		ModelMapper mapper = getValidationModel().getMapper();
		List<Object> modelChildren = new ArrayList<Object>();
		if (getValidationModel().getValidation() != null){
			if(getValidationModel().getValidation().getValidator() != null)
			{
				for (Validator v : getValidationModel().getValidation().getValidator()){
					modelChildren.add(mapper.getModelContainer(v));
				}
			}
		}
	return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ModelMapper mapper = getValidationModel().getMapper();
		if (evt.getPropertyName().equals(ValidationModel.PROPERTY_VALIDATORS)){
			if (evt.getOldValue() == null && evt.getNewValue() instanceof Validator){
				Validator v = (Validator) evt.getNewValue();
				ValidatorModel validatorModel = (ValidatorModel) mapper.getModelContainer(v);
				int index = getValidationModel().getValidation().getValidator().indexOf(validatorModel.getValidator());
				addChild(new ValidatorEditPart(validatorModel), index);
			}
			if (evt.getNewValue() == null && evt.getOldValue() instanceof Validator){
				List<EditPart> toDelete = new ArrayList<>();
				for (Object child : getChildren()){
					EditPart part = (EditPart) child;
					ValidatorModel model = (ValidatorModel) part.getModel();
					if (model.getValidator() == evt.getOldValue()){
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
