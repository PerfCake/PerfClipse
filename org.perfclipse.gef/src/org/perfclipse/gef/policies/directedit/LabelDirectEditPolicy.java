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


package org.perfclipse.gef.policies.directedit;

import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.perfclipse.gef.figures.ILabeledFigure;

public abstract class LabelDirectEditPolicy extends DirectEditPolicy {
	
	protected ILabeledFigure labeledFigure;

	public LabelDirectEditPolicy(ILabeledFigure labeledFigure) {
		if (labeledFigure == null)
			throw new IllegalArgumentException("figure must not be null");
		if (!(labeledFigure instanceof ILabeledFigure))
			throw new IllegalArgumentException("Figure has to be instance of ILabeledFigure");

		this.labeledFigure = labeledFigure;
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		labeledFigure.getLabel().setText((String) request.getCellEditor().getValue());
	}

}
