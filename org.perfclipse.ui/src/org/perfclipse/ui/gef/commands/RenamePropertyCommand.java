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
import org.perfclipse.model.PropertyModel;

/**
 * @author Jakub Knetl
 *
 */
public class RenamePropertyCommand extends Command {

	private PropertyModel property;

	private String newKey;
	private String oldKey;
	private String newValue;
	private String oldValue;
	/**
	 * @param property
	 * @param newKey
	 * @param newValue
	 */
	public RenamePropertyCommand(PropertyModel property, String newKey,
			String newValue) {
		super("Change property");
		this.property = property;
		this.newKey = newKey;
		this.newValue = newValue;
		this.oldKey = property.getProperty().getName();
		this.oldValue = property.getProperty().getValue();
	}
	@Override
	public void execute() {
		property.setName(newKey);
		property.setValue(newValue);
	}
	@Override
	public void undo() {
		property.setName(oldKey);
		property.setValue(oldValue);
	}
}
