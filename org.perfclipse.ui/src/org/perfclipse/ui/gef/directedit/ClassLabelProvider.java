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

package org.perfclipse.ui.gef.directedit;

import org.eclipse.jface.viewers.LabelProvider;
import org.perfclipse.ui.Utils;

public class ClassLabelProvider extends LabelProvider {
	@Override 
	public String getText(Object element){
		if (element instanceof Class){
			//TODO : check if it is component from perfcake.
			// If it is not then return full path		}
			Class<?> clazz = (Class<?>) element;
			return Utils.clazzToString(clazz);
		}
		return null;
	}
}