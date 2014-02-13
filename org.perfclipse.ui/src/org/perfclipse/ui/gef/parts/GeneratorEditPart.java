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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.perfclipse.model.GeneratorModel;
import org.perfclipse.ui.gef.figures.PerfCakeTwoPartRectangle;

//TODO : move implements to the superclass
public class GeneratorEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener {

	Label label;
	public GeneratorEditPart(GeneratorModel generatorModel) {
		setModel(generatorModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getGeneratorModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getGeneratorModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	public GeneratorModel getGeneratorModel(){
		return (GeneratorModel) getModel();
	}

	@Override
	protected IFigure createFigure() {
		String header = getGeneratorModel().getClazz() 
				+ " (" + getGeneratorModel().getThreads() + ")";
		PerfCakeTwoPartRectangle figure = new PerfCakeTwoPartRectangle(header, getDefaultSize());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		if (getGeneratorModel().getRunModel() != null)
			modelChildren.add(getGeneratorModel().getRunModel());

		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(GeneratorModel.PROPERTY_THREADS)){
			((PerfCakeTwoPartRectangle) getFigure()).setHeaderLabelText("changed by listener ;-)");
		}
	}
}
