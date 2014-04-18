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

package org.perfclipse.core.commands;

import org.eclipse.gef.commands.Command;
import org.perfcake.model.Property;
import org.perfclipse.core.model.IPropertyContainer;

/**
 * @author Jakub Knetl
 *
 */
public class AddPropertyCommand extends Command {

	private Property newProperty;
	private IPropertyContainer properties;

	public AddPropertyCommand(Property newProperty, IPropertyContainer properties) {
		super("Add property");
		this.newProperty = newProperty;
		this.properties = properties;
	}

	@Override
	public void execute() {
		properties.addProperty(newProperty);
	}

	@Override
	public void undo() {
		properties.removeProperty(newProperty);
	}
}
