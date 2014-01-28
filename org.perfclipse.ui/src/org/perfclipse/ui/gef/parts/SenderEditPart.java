package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfcake.model.Scenario;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class SenderEditPart extends AbstractPerfCakeNodeEditPart {

	public SenderEditPart(Scenario.Sender senderModel){
		setModel(senderModel);
	}

	public Scenario.Sender getSender(){
		return (Scenario.Sender) getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle(getSender().getClazz());
		figure.setPreferredSize(300, 40);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		modelChildren.add(0, getSender().getClazz());
		return modelChildren;
	}

}
