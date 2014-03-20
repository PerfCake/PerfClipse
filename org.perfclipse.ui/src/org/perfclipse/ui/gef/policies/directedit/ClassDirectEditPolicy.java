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

package org.perfclipse.ui.gef.policies.directedit;

import org.eclipse.gef.requests.DirectEditRequest;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.figures.ILabeledFigure;

/**
 * @author Jakub Knetl
 *
 */
public abstract class ClassDirectEditPolicy extends LabelDirectEditPolicy {

	public ClassDirectEditPolicy(ILabeledFigure labeledFigure) {
		super(labeledFigure);
	}
	
	/**
	 * Returns String representation of class object
	 * @param input selected object
	 * @return Label for given input or null if input is not instanceof Class
	 */
	protected String asString(Object input) {
		if (input instanceof Class<?>){
			Utils.clazzToString((Class<?>) input);
		}
		return null;
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		Object input = request.getCellEditor().getValue();
		labeledFigure.getLabel().setText(asString(input));
	}
	
	
}
