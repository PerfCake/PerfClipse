package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.commands.AddReporterCommand;

public class ReporterListEditPolicy extends AbstractListEditPolicy {

	ReportingModel reporting;
	ScenarioModel scenario;
	
	
	public ReporterListEditPolicy(ReportingModel reporting,
			ScenarioModel scenario) {
		super();
		this.reporting = reporting;
		this.scenario = scenario;
	}

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EditPart getInsertionReference(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object type = request.getNewObjectType();
		if (type == Reporter.class){
			Reporter reporter = (Reporter) request.getNewObject();
			if (reporting.getReporting() == null){
				reporting.createReporting();
				scenario.setReporting(reporting.getReporting());
			}
			
			return new AddReporterCommand(reporter, reporting);
		}
		
		return null;
	}

}
