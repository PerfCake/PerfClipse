package org.perfclipse.ui.gef.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.perfclipse.model.ScenarioModel;

public class PerfCakeEditPartFactory implements EditPartFactory {

	public PerfCakeEditPartFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof ScenarioModel){
			return new ScenarioEditPart((ScenarioModel) model);
		}
		if (model instanceof ScenarioModel.Generator){
			return new GeneratorEditPart((ScenarioModel.Generator) model);
		}
		if (model instanceof ScenarioModel.Sender){
			return new SenderEditPart((ScenarioModel.Sender) model);
		}
		if (model instanceof ScenarioModel.Reporting){
			return new ReportingEditPart((ScenarioModel.Reporting) model);
		}
		if (model instanceof ScenarioModel.Validation){
			return new ValidationEditPart((ScenarioModel.Validation) model);
		}
		if (model instanceof ScenarioModel.Validation.Validator){
			return new ValidatorEditPart((ScenarioModel.Validation.Validator) model);
		}
		if (model instanceof ScenarioModel.Messages){
			return new MessagesEditPart((ScenarioModel.Messages) model);
		}
		if (model instanceof ScenarioModel.Generator.Run){
			return new RunEditPart((ScenarioModel.Generator.Run) model);
		}
		if (model instanceof ScenarioModel.Messages.Message){
			return new MessageEditPart((ScenarioModel.Messages.Message) model);
		}
		if (model instanceof String){
			return new StringEditPart((String) model);
		}
		if (model instanceof ScenarioModel.Reporting.Reporter){
			return new ReporterEditPart((ScenarioModel.Reporting.Reporter) model);
		}
		if (model instanceof ScenarioModel.Reporting.Reporter.Destination){
			return new DestinationEditPart((ScenarioModel.Reporting.Reporter.Destination) model);
		}
		return null;
	}

}
