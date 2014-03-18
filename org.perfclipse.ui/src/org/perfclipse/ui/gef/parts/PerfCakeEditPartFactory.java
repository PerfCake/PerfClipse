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

package org.perfclipse.ui.gef.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.GeneratorModel;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PropertiesModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.model.RunModel;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.SenderModel;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;

public class PerfCakeEditPartFactory implements EditPartFactory {

	private ModelMapper mapper;

	public PerfCakeEditPartFactory() {
		//TODO: obtain model mapper from editor input.
		mapper = ModelMapper.getInstance();
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof ScenarioModel){
			return new ScenarioEditPart((ScenarioModel) model, mapper);
		}
		if (model instanceof GeneratorModel){
			return new GeneratorEditPart((GeneratorModel) model, mapper);
		}
		if (model instanceof SenderModel){
			return new SenderEditPart((SenderModel) model, mapper);
		}
		if (model instanceof ReportingModel){
			return new ReportingEditPart((ReportingModel) model, mapper);
		}
		if (model instanceof ValidationModel){
			return new ValidationEditPart((ValidationModel) model, mapper);
		}
		if (model instanceof ValidatorModel){
			return new ValidatorEditPart((ValidatorModel) model, mapper);
		}
		if (model instanceof MessagesModel){
			return new MessagesEditPart((MessagesModel) model, mapper);
		}
		if (model instanceof RunModel){
			return new RunEditPart((RunModel) model, mapper);
		}
		if (model instanceof MessageModel){
			return new MessageEditPart((MessageModel) model, mapper);
		}
		if (model instanceof String){
			return new StringEditPart((String) model, mapper);
		}
		if (model instanceof ReporterModel){
			return new ReporterEditPart((ReporterModel) model, mapper);
		}
		if (model instanceof DestinationModel){
			return new DestinationEditPart((DestinationModel) model, mapper);
		}
		if (model instanceof PropertiesModel){
			return new PropertiesEditPart((PropertiesModel) model, mapper);
		}
		if (model instanceof PropertyModel){
			return new PropertyEditPart((PropertyModel) model, mapper);
		}
		return null;
	}

}
