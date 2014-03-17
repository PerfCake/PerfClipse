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

import java.util.List;

import org.perfcake.model.Property;

/**
 * Class implementing this interface is able to have PerfCake properties.
 * 
 * @author Jakub Knetl
 */
public interface IPropertyContainer {

	/**
	 * Adds property to component on last position
	 * @param newProperty property to add
	 */
	public void addProperty(Property newProperty);

	/**
	 * Adds property to component on given position.
	 * 
	 * @param index position where this property will be placed
	 * @param newProperty property to add
	 */
	public void addProperty(int index, Property newProperty);

	/**
	 * Remove property from component. If such property does not exits
	 * then this mehtod does nothing.
	 * @param property property to remove.
	 */
	public void removeProperty(Property property);
	
	/**
	 * @return Return list of PerfCake Properties
	 */
	public List<Property> getProperty();

}
