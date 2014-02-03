/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2013 Jakub Knetl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.figures.PerfCakeTwoPartRectangle;

public class ValidationEditPart extends AbstractPerfCakeSectionEditPart {

	public ValidationEditPart(ScenarioModel.Validation validationModel){
		setModel(validationModel);
	}
	
	public ScenarioModel.Validation getValidation(){
		return (ScenarioModel.Validation) getModel();
	}
	@Override
	protected IFigure createFigure() {
		PerfCakeTwoPartRectangle figure = new PerfCakeTwoPartRectangle("Validation section", getDefaultSize());
//		figure.setPreferredSize(EMPTY_WIDTH, EMPTY_HEIGHT);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		modelChildren.addAll(getValidation().getValidator());
		return modelChildren;
	}

}
