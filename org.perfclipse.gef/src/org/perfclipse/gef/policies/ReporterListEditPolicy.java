package org.perfclipse.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.core.commands.AddReporterCommand;
import org.perfclipse.core.commands.MoveReporterCommand;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ReportingModel;

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
		int newIndex = calculateNewIndex(child, after);
		if (newIndex < 0)
			return null;

		return new MoveReporterCommand(reporting, newIndex, (ReporterModel) child.getModel());
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
