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

	public MessageModel(Message message) {
		if (message == null){
			throw new IllegalArgumentException("Message must not be null.");
		}
		this.message = message;
		listeners = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
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
	
	public void addProperty(Property property){
		getMessage().getProperty().add(property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getMessage().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public void addHeader(Header header){
		getMessage().getHeader().add(header);
		listeners.firePropertyChange(PROPERTY_HEADERS, null, header);
	}
	
	public void removeHeader(Header header){
		if (getMessage().getHeader().remove(header)){
			listeners.firePropertyChange(PROPERTY_HEADERS, header, null);
		}
	}
	
	public void addValidatorRef(ValidatorRef ref){
		getMessage().getValidatorRef().add(ref);
		listeners.firePropertyChange(PROPERTY_VALIDATOR_REFS, null, ref);
	}
	
	public void removeValidatorRef(ValidatorRef ref){
		if(getMessage().getValidatorRef().remove(ref)){
			listeners.firePropertyChange(PROPERTY_VALIDATOR_REFS, ref, null);
		}
	}
	
	public void setUri(String uri){
		String oldUri = getMessage().getUri();
		getMessage().setUri(uri);
		listeners.firePropertyChange(PROPERTY_URI, oldUri, uri);
	}
	
	public void setMultiplicity(String multiplicity){
		String oldMultiplicity = getMessage().getMultiplicity();
		getMessage().setMultiplicity(multiplicity);
		listeners.firePropertyChange(PROPERTY_MULTIPLICITY, oldMultiplicity, multiplicity);
	}
	
	

}
