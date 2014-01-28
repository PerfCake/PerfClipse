package org.perfclipse.ui.gef.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.perfcake.model.Scenario;

public class RunEditPart extends AbstractPerfCakeEditPart {

	Label label;
	
	public RunEditPart(Scenario.Generator.Run runModel){
		setModel(runModel);
	}
	
	public Scenario.Generator.Run getRun(){
		return (Scenario.Generator.Run) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		label = new Label();
		label.setText(getRun().getType() + " : " + getRun().getValue());
		return label;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

}
