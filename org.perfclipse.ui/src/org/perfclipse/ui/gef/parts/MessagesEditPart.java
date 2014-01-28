package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.perfcake.model.Scenario;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class MessagesEditPart extends AbstractPerfCakeNodeEditPart {

	public MessagesEditPart(Scenario.Messages messagesModel){
		setModel(messagesModel);
	}
	
	public Scenario.Messages getMessages(){
		return (Scenario.Messages) getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle("Messages section");
		figure.setPreferredSize(300, 100);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void addChild(EditPart child, int index){
		super.addChild(child, index);
		
	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>(getMessages().getMessage());
		modelChildren.add(0, "Messages section");
		return modelChildren;
	}
	
	@Override
	public void refresh(){
		super.refresh();
	}
	
}
