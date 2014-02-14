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

import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;

public class MessageModel {
	
	public static final String PROPERTY_HEADERS = "message-header";
	public static final String PROPERTY_PROPERTIES = "message-property";
	public static final String PROPERTY_VALIDATOR_REFS = "message-validator-ref";
	public static final String PROPERTY_URI = "message-uri";
	public static final String PROPERTY_MULTIPLICITY = "message-multiplicity";

	private Message message;
	private PropertyChangeSupport listeners;
	
	protected List<HeaderModel> headerModel;
	protected List<PropertyModel> propertyModel;
	protected List<ValidatorRefModel> validatorRefModel;

	public MessageModel(Message message) {
		this.message = message;
		listeners = new PropertyChangeSupport(this);
		
		headerModel = new ArrayList<>();
		propertyModel = new ArrayList<>();
		validatorRefModel = new ArrayList<>();

		if (message != null){
			if (message.getHeader() != null){
				for (Header h: message.getHeader()){
					headerModel.add(new HeaderModel(h));
				}
			}
			
			if (message.getProperty() != null){
				for (Property p : message.getProperty()){
					propertyModel.add(new PropertyModel(p));
				}
			}
			
			if (message.getValidatorRef() != null){
				for (ValidatorRef v : message.getValidatorRef()){
					validatorRefModel.add(new ValidatorRefModel(v));
				}
			}
			
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	public void addPropertyModel(PropertyModel propertyModel){
		this.addProperty(propertyModel.getProperty());
		this.propertyModel.add(propertyModel);
	}
	
	public void removePropertyModel(PropertyModel propertyModel){
		this.removeProperty(propertyModel.getProperty());
		this.propertyModel.remove(propertyModel);
	}
	
	public void addHeaderModel(HeaderModel headerModel){
		this.headerModel.add(headerModel);
		this.addHeader(headerModel.getHeader());
	}
	
	public void removeHeaderModel(HeaderModel headerModel){
		this.headerModel.remove(headerModel);
		this.removeHeader(headerModel.getHeader());
	}
	
	public void addValidatorRefModel(ValidatorRefModel validatorRefModel){
		this.validatorRefModel.add(validatorRefModel);
		this.addValidatorRef(validatorRefModel.getValidatorRef());
	}
	
	public void removeValidatorRefModel(ValidatorRefModel validatorRefModel){
		this.validatorRefModel.remove(validatorRefModel);
		this.removeValidatorRef(validatorRefModel.getValidatorRef());
	}
	
	/**
	 * Do not modify list using getList().add() since it will not fire
	 * propertyChangeEvent and the view of the model won't be refreshed.
	 * Use add* and remove* methods instead.
	 * @return List of PerfClipse model
	 */	
	public List<HeaderModel> getHeaderModel() {
		return headerModel;
	}

	/**
	 * Do not modify list using getList().add() since it will not fire
	 * propertyChangeEvent and the view of the model won't be refreshed.
	 * Use add* and remove* methods instead.
	 * @return List of PerfClipse model
	 */
	public List<PropertyModel> getPropertyModel() {
		return propertyModel;
	}

	/**
	 * Do not modify list using getList().add() since it will not fire
	 * propertyChangeEvent and the view of the model won't be refreshed.
	 * Use add* and remove* methods instead.
	 * @return List of PerfClipse model
	 */
	public List<ValidatorRefModel> getValidatorRefModel() {
		return validatorRefModel;
	}

	/**
	 * This method should not be used for modifying message (in a way getMessage().setUri(uri))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Message
	 */
	public Message getMessage(){
		return message;
	}
	
	protected void addProperty(Property property){
		getMessage().getProperty().add(property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	protected void removeProperty(Property property){
		if (getMessage().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	protected void addHeader(Header header){
		getMessage().getHeader().add(header);
		listeners.firePropertyChange(PROPERTY_HEADERS, null, header);
	}
	
	protected void removeHeader(Header header){
		if (getMessage().getHeader().remove(header)){
			listeners.firePropertyChange(PROPERTY_HEADERS, header, null);
		}
	}
	
	protected void addValidatorRef(ValidatorRef ref){
		getMessage().getValidatorRef().add(ref);
		listeners.firePropertyChange(PROPERTY_VALIDATOR_REFS, null, ref);
	}
	
	protected void removeValidatorRef(ValidatorRef ref){
		if(getMessage().getValidatorRef().remove(ref)){
			listeners.firePropertyChange(PROPERTY_VALIDATOR_REFS, ref, null);
		}
	}
	
	public void setUri(String uri){
		String oldUri = getMessage().getUri();
		getMessage().setUri(uri);
		listeners.firePropertyChange(PROPERTY_URI, oldUri, uri);
	}
	
	public String getUri(){
		return getMessage().getUri();
	}
	
	public void setMultiplicity(String multiplicity){
		String oldMultiplicity = getMessage().getMultiplicity();
		getMessage().setMultiplicity(multiplicity);
		listeners.firePropertyChange(PROPERTY_MULTIPLICITY, oldMultiplicity, multiplicity);
	}
	
	public String getMultiplicity(){
		return getMessage().getMultiplicity();
	}

}
