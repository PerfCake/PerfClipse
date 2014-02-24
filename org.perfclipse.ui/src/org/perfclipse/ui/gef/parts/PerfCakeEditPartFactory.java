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
import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.model.RunModel;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.SenderModel;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;

public class PerfCakeEditPartFactory implements EditPartFactory {

	public PerfCakeEditPartFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		 if (model instanceof ScenarioModel){
             return new ScenarioEditPart((ScenarioModel) model);
     }
     if (model instanceof GeneratorModel){
             return new GeneratorEditPart((GeneratorModel) model);
     }
     if (model instanceof SenderModel){
             return new SenderEditPart((SenderModel) model);
     }
     if (model instanceof ReportingModel){
             return new ReportingEditPart((ReportingModel) model);
     }
     if (model instanceof ValidationModel){
             return new ValidationEditPart((ValidationModel) model);
     }
     if (model instanceof ValidatorModel){
             return new ValidatorEditPart((ValidatorModel) model);
     }
     if (model instanceof MessagesModel){
             return new MessagesEditPart((MessagesModel) model);
     }
     if (model instanceof RunModel){
             return new RunEditPart((RunModel) model);
     }
     if (model instanceof MessageModel){
             return new MessageEditPart((MessageModel) model);
     }
     if (model instanceof String){
             return new StringEditPart((String) model);
     }
     if (model instanceof ReporterModel){
             return new ReporterEditPart((ReporterModel) model);
     }
     if (model instanceof DestinationModel){
             return new DestinationEditPart((DestinationModel) model);
     }
     return null;
	}

}
