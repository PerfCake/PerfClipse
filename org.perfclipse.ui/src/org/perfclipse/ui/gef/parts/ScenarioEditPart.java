package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
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
		figure.setLayoutManager(new FreeformLayout());
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
		
		return modelChildren;
	}
	
	public Scenario getScenario(){
		return (Scenario) getModel();
	}
	

}
