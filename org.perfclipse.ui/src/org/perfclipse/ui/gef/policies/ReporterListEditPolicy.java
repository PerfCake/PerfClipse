package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.ui.gef.commands.AddReporterCommand;

public class ReporterListEditPolicy extends AbstractListEditPolicy {

	ReportingModel reporting;
	
	
	public ReporterListEditPolicy(ReportingModel reporting) {
		super();
		this.reporting = reporting;
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
			return new AddReporterCommand(reporter, reporting);
		}
		
		return null;
	}

}
