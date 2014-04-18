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

package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.core.model.IPropertyContainer;
import org.perfclipse.core.model.PropertyModel;

/**
 * @author Jakub Knetl
 *
 */
public class MovePropertyCommand extends Command {

	private IPropertyContainer propertyContainer;
	private int newIndex;
	private int oldIndex;
	private PropertyModel property;
	/**
	 * @param propertyContainer
	 * @param newIndex
	 * @param property
	 */
	public MovePropertyCommand(IPropertyContainer propertyContainer,
			int newIndex, PropertyModel property) {
		super("Move property");
		this.propertyContainer = propertyContainer;
		this.newIndex = newIndex;
		this.property = property;
		oldIndex = propertyContainer.getProperty().indexOf(property.getProperty());
	}
	@Override
	public void execute() {
		propertyContainer.removeProperty(property.getProperty());
		propertyContainer.addProperty(newIndex, property.getProperty());;
	}
	@Override
	public void undo() {
		propertyContainer.removeProperty(property.getProperty());
		propertyContainer.addProperty(oldIndex, property.getProperty());;
	}
	
	

	
}
