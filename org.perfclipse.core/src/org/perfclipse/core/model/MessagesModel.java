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

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;

public class MessagesModel extends PerfClipseModel {

	public  static final String PROPERTY_MESSAGE = "messages-message";

	private Messages messages;
	private ScenarioModel scenario;

	public MessagesModel(Messages messages, ScenarioModel scenario,
			ModelMapper mapper){
		super(mapper);
//		if (messages == null){
//			throw new IllegalArgumentException("Messages must not be null");
//		}
		if (scenario == null){
			throw new IllegalArgumentException("Scenario must not be null.");
		}
		this.scenario = scenario;
		this.messages = messages;
	}
	
	/**
	 * This method should not be used for modifying messages (in a way getMessages().add(message))
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Messages
	 */
	public Messages getMessages(){
		return messages;
	}
	
	public void addMessage(Message m){
		if (messages == null){
			createMessages();
		}
		addMessage(getMessages().getMessage().size(), m);
	}
	public void addMessage(int index, Message m){
		if (messages == null){
			createMessages();
		}
		getMessages().getMessage().add(index, m);
		getListeners().firePropertyChange(PROPERTY_MESSAGE, null, m);
	}
	
	public void removeMessage(Message m){
		if (getMessages().getMessage().remove(m)){
			getListeners().firePropertyChange(PROPERTY_MESSAGE, m, null);
		}
		if (getMessages().getMessage().isEmpty()){
			removeMessages();
		}
	}
	
	private void createMessages(){
		ObjectFactory f = new ObjectFactory();
		messages = f.createScenarioMessages(); 
		scenario.setMessages(messages);
		getMapper().addEntry(messages, this);
	}
	
	private void removeMessages(){
		getMapper().removeEntry(messages);
		messages = null;
		scenario.setMessages(messages);
	}
	
	
}
