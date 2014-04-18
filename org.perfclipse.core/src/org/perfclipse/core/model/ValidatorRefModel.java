package org.perfclipse.core.model;

import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;

public class ValidatorRefModel extends PerfClipseModel{

	public static final String PROPERTY_ID = "validatorRef-id";
	
	private ValidatorRef validatorRef;

	public ValidatorRefModel(ValidatorRef validatorRef, ModelMapper mapper) {
		super(mapper);
		if (validatorRef == null){
			throw new IllegalArgumentException("ValidatorRef must not be null.");
		}
		
		this.validatorRef = validatorRef;
	}

	
	/**
	 * This method should not be used for modifying validatorRef (in a way getValidatorRef().setId()))
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of validatorRef
	 */
	public ValidatorRef getValidatorRef() {
		return validatorRef;
	}
	
	public void setId(String id){
		String oldId = getValidatorRef().getId();
		getValidatorRef().setId(id);
		getListeners().firePropertyChange(PROPERTY_ID, oldId, id);
	}
}
