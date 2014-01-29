package org.perfclipse.ui.gef.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.perfclipse.model.ScenarioModel;

public class RunEditPart extends AbstractPerfCakeEditPart {

	Label label;
	
	public RunEditPart(ScenarioModel.Generator.Run runModel){
		setModel(runModel);
	}
	
	public ScenarioModel.Generator.Run getRun(){
		return (ScenarioModel.Generator.Run) getModel(); 
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
