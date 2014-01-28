package org.perfclipse.ui.gef.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;

public class StringEditPart extends AbstractPerfCakeEditPart {

	public StringEditPart(String name){
		setModel(name);
	}
	
	public String getName(){
		return (String) getModel();
	}
	@Override
	protected IFigure createFigure() {
		Label label = new Label();
		label.setText(getName());
		Dimension d = new Dimension(250,  20);
		label.setSize(d);
		label.setPreferredSize(d);
		label.setMinimumSize(d);
		return label;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

}
