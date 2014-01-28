package org.perfclipse.ui.gef.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.perfcake.model.Scenario;

public class PerfCakeEditPartFactory implements EditPartFactory {

	public PerfCakeEditPartFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof Scenario){
			return new ScenarioEditPart((Scenario) model);
		}
		if (model instanceof Scenario.Generator){
			return new GeneratorEditPart((Scenario.Generator) model);
		}
		if (model instanceof Scenario.Sender){
			return new SenderEditPart((Scenario.Sender) model);
		}
		if (model instanceof Scenario.Reporting){
			return new ReportingEditPart((Scenario.Reporting) model);
		}
		if (model instanceof Scenario.Validation){
			return new ValidationEditPart((Scenario.Validation) model);
		}
		if (model instanceof Scenario.Validation.Validator){
			return new ValidatorEditPart((Scenario.Validation.Validator) model);
		}
		if (model instanceof Scenario.Messages){
			return new MessagesEditPart((Scenario.Messages) model);
		}
		if (model instanceof Scenario.Generator.Run){
			return new RunEditPart((Scenario.Generator.Run) model);
		}
		if (model instanceof Scenario.Messages.Message){
			return new MessageEditPart((Scenario.Messages.Message) model);
		}
		if (model instanceof String){
			return new StringEditPart((String) model);
		}
		if (model instanceof Scenario.Reporting.Reporter){
			return new ReporterEditPart((Scenario.Reporting.Reporter) model);
		}
		if (model instanceof Scenario.Reporting.Reporter.Destination){
			return new DestinationEditPart((Scenario.Reporting.Reporter.Destination) model);
		}
		return null;
	}

}
