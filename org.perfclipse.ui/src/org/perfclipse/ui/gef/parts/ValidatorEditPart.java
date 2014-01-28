package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfcake.model.Scenario;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class ValidatorEditPart extends AbstractPerfCakeNodeEditPart {

	public ValidatorEditPart(Scenario.Validation.Validator validatorModel){
		setModel(validatorModel);
	}
	
	public Scenario.Validation.Validator getValidator(){
		return (Scenario.Validation.Validator) getModel();
	}
	@Override
	protected IFigure createFigure() {
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle(getValidator().getClazz());
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
		modelChildren.add(getValidator().getClazz());
		return modelChildren;
	}

}
