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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.perfclipse.core.commands.EditMessageUriCommand;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.gef.figures.ILabeledFigure;

public class MessageDirectEditPolicy extends LabelDirectEditPolicy {

	protected MessageModel model;

	public MessageDirectEditPolicy(MessageModel model, ILabeledFigure labeledFigure) {
		super(labeledFigure);
		if (model == null)
			throw new IllegalArgumentException("Model must not be null");
		
		this.model = model;
	}

	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		return new EditMessageUriCommand(model, (String) request.getCellEditor().getValue());
	}

}
