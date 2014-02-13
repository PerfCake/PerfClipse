package org.perfclipse.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;

public class ValidatorRefModel {

	public static final String PROPERTY_ID = "validatorRef-id";
	
	private ValidatorRef validatorRef;
	private PropertyChangeSupport listeners;

	public ValidatorRefModel(ValidatorRef validatorRef) {
		this.validatorRef = validatorRef;
		listeners = new PropertyChangeSupport(this);
	}

	
	/**
	 * This method should not be used for modifying validatorRef (in a way getValidatorRef().setId()))
	 * since these changes would not fire PropertyChange listeners which implies that
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
		listeners.firePropertyChange(PROPERTY_ID, oldId, id);
	}
	
	public String getId(){
		return getValidatorRef().getId();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
}
