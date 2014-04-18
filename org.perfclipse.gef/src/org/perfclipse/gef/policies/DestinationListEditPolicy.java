package org.perfclipse.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.core.commands.AddDestinationCommand;
import org.perfclipse.core.commands.MoveDestinationCommand;
import org.perfclipse.core.model.DestinationModel;
import org.perfclipse.core.model.ReporterModel;

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
