package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class ValidatorEditPart extends AbstractPerfCakeNodeEditPart {

	public ValidatorEditPart(ScenarioModel.Validation.Validator validatorModel){
		setModel(validatorModel);
	}
	
	public ScenarioModel.Validation.Validator getValidator(){
		return (ScenarioModel.Validation.Validator) getModel();
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
