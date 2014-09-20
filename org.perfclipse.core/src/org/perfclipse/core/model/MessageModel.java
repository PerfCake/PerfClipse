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

package org.perfclipse.core.model;

import java.util.List;

import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;

public class MessageModel extends PerfClipseModel implements IPropertyContainer {
	
	public static final String PROPERTY_HEADERS = "message-header";
	public static final String PROPERTY_PROPERTIES = "message-property";
	public static final String PROPERTY_VALIDATOR_REFS = "message-validator-ref";
	public static final String PROPERTY_URI = "message-uri";
	public static final String PROPERTY_MULTIPLICITY = "message-multiplicity";
	private static final String PROPERTY_CONTENT = "message-content";

	private Message message;

	public MessageModel(Message message, ModelMapper mapper) {
		super(mapper);
		if (message == null){
			throw new IllegalArgumentException("Message must not be null.");
		}
		this.message = message;
	}
	
	/**
	 * This method should not be used for modifying message (in a way getMessage().setUri(uri))
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Message
	 */
	public Message getMessage(){
		return message;
	}
	
	public void addProperty(Property Property){
		addProperty(getMessage().getProperty().size(), Property);
	}
	
	public void addProperty(int index, Property property){
		getMessage().getProperty().add(index, property);
		getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getMessage().getProperty().remove(property)){
			getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public List<Property> getProperty(){
		return getMessage().getProperty();
	}
	
	public void addHeader(Header header){
		addHeader(getMessage().getHeader().size(), header);
	}
	public void addHeader(int index, Header header){
		getMessage().getHeader().add(index, header);
		getListeners().firePropertyChange(PROPERTY_HEADERS, null, header);
	}
	
	public void removeHeader(Header header){
		if (getMessage().getHeader().remove(header)){
			getListeners().firePropertyChange(PROPERTY_HEADERS, header, null);
		}
	}
	
	public void addValidatorRef(ValidatorRef ref){
		addValidatorRef(getMessage().getValidatorRef().size(), ref);
	}
	public void addValidatorRef(int index, ValidatorRef ref){
		getMessage().getValidatorRef().add(index, ref);
		getListeners().firePropertyChange(PROPERTY_VALIDATOR_REFS, null, ref);
	}
	
	public void removeValidatorRef(ValidatorRef ref){
		if(getMessage().getValidatorRef().remove(ref)){
			getListeners().firePropertyChange(PROPERTY_VALIDATOR_REFS, ref, null);
		}
	}
	
	public void setUri(String uri){
		String oldUri = getMessage().getUri();
		getMessage().setUri(uri);
		getListeners().firePropertyChange(PROPERTY_URI, oldUri, uri);
	}
	
	public void setMultiplicity(String multiplicity){
		String oldMultiplicity = getMessage().getMultiplicity();
		getMessage().setMultiplicity(multiplicity);
		getListeners().firePropertyChange(PROPERTY_MULTIPLICITY, oldMultiplicity, multiplicity);
	}
	
	public void setContent(String content){
		String oldContent = getMessage().getContent();
		getMessage().setContent(content);
		getListeners().firePropertyChange(PROPERTY_CONTENT, oldContent, content);
	}
	
	

}
