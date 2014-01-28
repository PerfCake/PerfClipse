package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfcake.model.Scenario;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class DestinationEditPart extends AbstractPerfCakeNodeEditPart {

	public DestinationEditPart(Scenario.Reporting.Reporter.Destination destinationModel){
		setModel(destinationModel);
	}
	
	public Scenario.Reporting.Reporter.Destination getDestination(){
		return (Scenario.Reporting.Reporter.Destination) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle(getDestination().getClazz());
		figure.setPreferredSize(150, 40);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		modelChildren.add(getDestination().getClazz());
		return modelChildren;
	}

}
