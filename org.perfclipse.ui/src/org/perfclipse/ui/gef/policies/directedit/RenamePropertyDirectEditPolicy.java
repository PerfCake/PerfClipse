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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.DirectEditRequest;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.commands.EditPropertyNameCommand;
import org.perfclipse.ui.gef.commands.EditPropertyValueCommand;
import org.perfclipse.ui.gef.figures.ILabeledFigure;

/**
 * @author Jakub Knetl
 *
 */
public class RenamePropertyDirectEditPolicy extends LabelDirectEditPolicy {
	
	private PropertyModel model;
	
	public RenamePropertyDirectEditPolicy(PropertyModel model,
			ILabeledFigure labeledFigure) {
		super(labeledFigure);
		this.model = model;
	}



	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		String input = (String) request.getCellEditor().getValue(); 
		String[] parts = input.split(":");


		if (parts.length == 2){
			CompoundCommand command = new CompoundCommand("Edit property");
			command.add(new EditPropertyNameCommand(model, parts[0].trim()));
			command.add(new EditPropertyValueCommand(model, parts[1].trim()));

			return command;
		}
		
		return null;
	}

}
