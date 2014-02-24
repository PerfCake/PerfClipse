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
import org.perfcake.model.Scenario.Generator;
import org.perfcake.model.Scenario.Generator.Run;
import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Reporting;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.model.Scenario.Sender;
import org.perfcake.model.Scenario.Validation;
import org.perfcake.model.Scenario.Validation.Validator;
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
		if (model instanceof Generator){
			return new GeneratorEditPart(new GeneratorModel((Generator) model));
		}
		if (model instanceof Sender){
			return new SenderEditPart(new SenderModel((Sender) model));
		}
		if (model instanceof Reporting){
			return new ReportingEditPart(new ReportingModel((Reporting) model));
		}
		if (model instanceof Validation){
			return new ValidationEditPart(new ValidationModel((Validation) model));
		}
		if (model instanceof Validator){
			return new ValidatorEditPart(new ValidatorModel((Validator) model));
		}
		if (model instanceof Messages){
			return new MessagesEditPart(new MessagesModel((Messages) model));
		}
		if (model instanceof Run){
			return new RunEditPart(new RunModel((Run) model));
		}
		if (model instanceof Message){
			return new MessageEditPart(new MessageModel((Message) model));
		}
		if (model instanceof String){
			return new StringEditPart((String) model);
		}
		if (model instanceof Reporter){
			return new ReporterEditPart(new ReporterModel((Reporter) model));
		}
		if (model instanceof Destination){
			return new DestinationEditPart( new DestinationModel((Destination) model));
		}
		return null;
	}

}
