package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class MessageEditPart extends AbstractPerfCakeNodeEditPart {

	
	public MessageEditPart(ScenarioModel.Messages.Message modelMessage){
		setModel(modelMessage);
	}
	
	public ScenarioModel.Messages.Message getMessage(){
		return (ScenarioModel.Messages.Message) getModel();
	}
	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle(getMessage().getUri().toString());
		figure.setPreferredSize(150, 40);

		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		modelChildren.add(getMessage().getUri().toString());
		return modelChildren;

	}

}
