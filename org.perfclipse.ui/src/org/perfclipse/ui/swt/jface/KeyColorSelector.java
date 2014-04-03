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

package org.perfclipse.ui.swt.jface;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.swt.widgets.Composite;

/**
 * Color selector which owns in addition to {@link ColorSelector} instance string
 * field, which is used as a key for given selector.
 * 
 * @author Jakub Knetl
 *
 */
public class KeyColorSelector extends ColorSelector {

	private String key;

	/**
	 * Creates new KeyColorSelector instance.
	 * @param parent parent composite to which ColorSelector will be added
	 * @param key key of ColorSelector
	 */
	public KeyColorSelector(Composite parent, String key) {
		super(parent);
		if (key == null){
			throw new IllegalArgumentException("Key must not be null");
		}
		this.key = key;
	}

	/**
	 * @return key which identifies current instance.
	 */
	public String getKey() {
		return key;
	}
}
