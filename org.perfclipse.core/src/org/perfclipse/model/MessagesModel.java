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

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;

public class MessagesModel {

	public  static final String PROPERTY_MESSAGE = "messages-message";

	private PropertyChangeSupport listeners;
	private Messages messages;
	
	protected List<MessageModel> messageModel;
	
	public MessagesModel(Messages messages){
		this.messages = messages;
		listeners = new PropertyChangeSupport(this);
		
		messageModel = new ArrayList<>();

		if (messages != null){
			if (messages.getMessage() != null){
				for (Message m : messages.getMessage()){
					messageModel.add(new MessageModel(m));
				}
			}
		}
	}
	
	public void addMessageModel(MessageModel messageModel){
		this.messageModel.add(messageModel);
		this.addMessage(messageModel.getMessage());
	}
	
	public void removeMessageModel(MessageModel messageModel){
		this.messageModel.remove(messageModel);
		this.removeMessage(messageModel.getMessage());
	}

	/**
	 * Do not modify list using getList().add() since it will not fire
	 * propertyChangeEvent and the view of the model won't be refreshed.
	 * Use add* and remove* methods instead.
	 * @return List of PerfClipse model
	 */	
	public List<MessageModel> getMessageModel() {
		return messageModel;
	}

	/**
	 * This method should not be used for modifying messages (in a way getMessages().add(message))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Messages
	 */
	public Messages getMessages(){
		return messages;
	}
	
	protected void addMessage(Message m){
		if (messages == null){
			messages = createMessages();
		}
		getMessages().getMessage().add(m);
		listeners.firePropertyChange(PROPERTY_MESSAGE, null, m);
	}
	
	protected void removeMessage(Message m){
		if (getMessages().getMessage().remove(m)){
			listeners.firePropertyChange(PROPERTY_MESSAGE, m, null);
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	private Scenario.Messages createMessages(){
		ObjectFactory f = new ObjectFactory();
		return f.createScenarioMessages();
	}

}
