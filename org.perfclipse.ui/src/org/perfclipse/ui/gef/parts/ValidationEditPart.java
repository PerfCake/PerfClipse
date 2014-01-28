package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfcake.model.Scenario;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class ValidationEditPart extends AbstractPerfCakeNodeEditPart {

	public ValidationEditPart(Scenario.Validation validationModel){
		setModel(validationModel);
	}
	
	public Scenario.Validation getValidation(){
		return (Scenario.Validation) getModel();
	}
	@Override
	protected IFigure createFigure() {
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle("Validation section");
		figure.setPreferredSize(300, 100);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		modelChildren.add("Validation section");
		modelChildren.addAll(getValidation().getValidator());
		return modelChildren;
	}

}
