package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.ui.gef.commands.AddDestinationCommand;
import org.perfclipse.ui.gef.commands.MoveDestinationCommand;

public class DestinationListEditPolicy extends AbstractListEditPolicy {
	
	private ReporterModel reporter;
	
	

	public DestinationListEditPolicy(ReporterModel reporter) {
		this.reporter = reporter;
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

		return new MoveDestinationCommand(reporter, newIndex, (DestinationModel) child.getModel());
	}
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request.getNewObjectType() == Destination.class){
			Destination destination = (Destination) request.getNewObject();
			return new AddDestinationCommand(destination, reporter);
			
		}
		return null;
	}

}
