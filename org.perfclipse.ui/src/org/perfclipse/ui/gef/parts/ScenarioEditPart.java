package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.perfcake.model.Scenario;

public class ScenarioEditPart extends AbstractPerfCakeEditPart {

	private static final int BORDER_PADDING = 1;

	
	public ScenarioEditPart(Scenario scenarioModel){
		setModel(scenarioModel);
	}
	@Override
	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		GridLayout layout = new GridLayout(2, false);
//		FreeformLayout layout = new FreeformLayout();
//		org.eclipse.draw2d.FlowLayout layout = new org.eclipse.draw2d.FlowLayout(true);
		figure.setLayoutManager(layout);
		figure.setBorder(new MarginBorder(BORDER_PADDING));

		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		modelChildren.add(getScenario().getGenerator());
		modelChildren.add(getScenario().getSender());
		if (getScenario().getMessages() != null)
			modelChildren.add(getScenario().getMessages());
		if (getScenario().getValidation() != null)
			modelChildren.add(getScenario().getValidation());
		if (getScenario().getReporting() != null)
			modelChildren.add(getScenario().getReporting());
		return modelChildren;
	}
	
	public Scenario getScenario(){
		return (Scenario) getModel();
	}
	

}
