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

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.perfclipse.ui.gef.layout.LayoutUtils;
import org.perfclipse.ui.gef.layout.ScenarioFreeformLayout;

public abstract class AbstractPerfCakeSectionEditPart extends
		AbstractPerfCakeNodeEditPart {

	protected Dimension getDefaultSize() {
		Dimension defaultSize = new Dimension(0, 0);
		LayoutUtils utils = LayoutUtils.getInstance();
		Rectangle defaultConstraint = utils.getDefaultConstraint(this);

		defaultSize = new Dimension(defaultConstraint.width, defaultConstraint.height);
		return defaultSize;
	}



	//TODO : is double validate necessary?
	@Override
	public void refreshVisuals() {
		getFigure().validate();
		ScenarioFreeformLayout scenarioLayout = (ScenarioFreeformLayout) getFigure().getParent().getLayoutManager();
		Rectangle constraint = (Rectangle) scenarioLayout.getConstraint(getFigure());
		
		int requiredHeight = Math.max(getFigureRequiredHeight(), LayoutUtils.getInstance().getDefaultConstraint(this).height);
		
		scenarioLayout.setConstraint(getFigure(),
				new Rectangle(constraint.x, constraint.y, constraint.width, requiredHeight));
		scenarioLayout.resizeSiblings(this);
	
		Dimension d = getContentPane().getPreferredSize().getCopy();
		d.height = requiredHeight;
		getContentPane().setPreferredSize(d);
		getFigure().validate();
		
	}


}