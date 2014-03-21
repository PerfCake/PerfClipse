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

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Validation;
import org.perfcake.model.Scenario.Validation.Validator;

public class ValidationModel extends PerfClipseModel {

	public static final String PROPERTY_VALIDATORS = "validation-validators";
	
	private Validation validation;
	
	public ValidationModel(Validation validation, ModelMapper mapper){
		super(mapper);
//		if (validation == null){
//			throw new IllegalArgumentException("Validation must not be null");
//		}
		this.validation = validation;
	}

	/**
	 * This method should not be used for modifying Validation (in a way getValidation().getValidator().add()))
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Validation
	 */
	public Validation getValidation() {
		return validation;
	}
	
	public void createValidation(){
		//no need to fire property change since it will not change the view
		if (validation == null){
			ObjectFactory f = new ObjectFactory();
			validation = f.createScenarioValidation();
		}
	}
	
	public void addValidator(Validator validator){
		addValidator(getValidation().getValidator().size(), validator);
	}
	public void addValidator(int index, Validator validator){
		getValidation().getValidator().add(index, validator);
		getListeners().firePropertyChange(PROPERTY_VALIDATORS, null, validator);
	}
	
	public void removeValidator(Validator validator){
		if (getValidation().getValidator().remove(validator)){
			getListeners().firePropertyChange(PROPERTY_VALIDATORS, validator, null);
		}
	}

}
