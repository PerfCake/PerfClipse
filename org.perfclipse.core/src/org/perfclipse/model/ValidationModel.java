/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
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

package org.perfclipse.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.perfcake.model.Scenario.Validation;
import org.perfcake.model.Scenario.Validation.Validator;

public class ValidationModel {

	public static final String PROPERTY_VALIDATORS = "validation-validators";
	
	private Validation validation;
	private PropertyChangeSupport listeners;
	
	protected List<ValidatorModel> validatorModel;
	
	public ValidationModel(Validation validation){
		this.validation = validation;
		listeners = new PropertyChangeSupport(this);
		
		validatorModel = new ArrayList<>();

		if (validation != null){
			if (validation.getValidator() != null){
				for (Validator v: validation.getValidator()){
					validatorModel.add(new ValidatorModel(v));
				}
			}
		}
	}
	
	public void addValidatorModel(ValidatorModel validatorModel){
		this.validatorModel.add(validatorModel);
		this.addValidator(validatorModel.getValidator());
	}
	
	public void removeValidatorModel(ValidatorModel validatorModel){
		this.validatorModel.remove(validatorModel);
		this.removeValidator(validatorModel.getValidator());
	}

	/**
	 * Do not modify list using getList().add() since it will not fire
	 * propertyChangeEvent and the view of the model won't be refreshed.
	 * Use add* and remove* methods instead.
	 * @return List of PerfClipse model
	 */
	public List<ValidatorModel> getValidatorModel() {
		return validatorModel;
	}

	/**
	 * This method should not be used for modifying Validation (in a way getValidation().getValidator().add()))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Validation
	 */
	public Validation getValidation() {
		return validation;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	

	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	protected void addValidator(Validator validator){
		getValidation().getValidator().add(validator);
		listeners.firePropertyChange(PROPERTY_VALIDATORS, null, validator);
	}
	
	protected void removeValidator(Validator validator){
		if (getValidation().getValidator().remove(validator)){
			listeners.firePropertyChange(PROPERTY_VALIDATORS, validator, null);
		}
	}

}
