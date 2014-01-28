package org.perfclipse.ui.gef.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.perfcake.model.Scenario;

public class PerfCakeEditPartFactory implements EditPartFactory {

	public PerfCakeEditPartFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof Scenario){
			return new ScenarioEditPart((Scenario) model);
		}
		if (model instanceof Scenario.Generator){
			return new GeneratorEditPart((Scenario.Generator) model);
		}
		return null;
	}

}
