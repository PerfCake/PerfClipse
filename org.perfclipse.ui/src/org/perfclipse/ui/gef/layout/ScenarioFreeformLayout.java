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

package org.perfclipse.ui.gef.layout;

import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.perfclipse.ui.gef.RectanglePositionComparator;

public class ScenarioFreeformLayout extends FreeformLayout {
	
	private LayoutUtils utils;
	
	public ScenarioFreeformLayout() {
		super();
		utils = LayoutUtils.getInstance();
	}

	@Override
	public void setConstraint(IFigure figure, java.lang.Object newConstraint){
		if (figure != null && newConstraint != null){
			figure.setBounds((Rectangle) newConstraint);
		}
		super.setConstraint(figure, newConstraint);
	}

	public void resizeSiblings(EditPart part) {
		if (!(part instanceof AbstractGraphicalEditPart))
			return;

		if (part.getParent() == null)
			return;

		IFigure figure = ((AbstractGraphicalEditPart) part).getFigure();

		for (Object sibling : part.getParent().getChildren()){

			// if current sibling is currently resized then skip (there will be always total intersection)
			if ((EditPart) sibling == part)
				continue;

			//if current sibling is not graphicalEditPart then it does not need to be resized (since it has no graphical representation)
			if (!(sibling instanceof AbstractGraphicalEditPart))
				continue;

			AbstractGraphicalEditPart siblingPart = ((AbstractGraphicalEditPart) sibling);
			IFigure siblingFigure = siblingPart.getFigure();

			if (siblingFigure.intersects(figure.getBounds())){
				Rectangle intersection = figure.getBounds().getCopy().intersect(siblingFigure.getBounds());

				Rectangle defaultConstraint = getDefaultConstraint(part);
				Rectangle siblingDefaultConstraint = getDefaultConstraint(siblingPart);
				RectanglePositionComparator cmp = new RectanglePositionComparator();

				if (cmp.compare(defaultConstraint, siblingDefaultConstraint) > 0){
					//move sibling
					Rectangle old = (Rectangle) getConstraint(figure);
					Rectangle newIntersectedFigureConstraint = new Rectangle(old.x, old.y + intersection.height + SizeConstants.TOP_LEVEL_VERTICAL_GAP, old.width, old.height);
					setConstraint(figure, newIntersectedFigureConstraint);
					resizeSiblings(part);
				} else{
					//move the figure
					Rectangle old = (Rectangle) getConstraint(siblingFigure);
					Rectangle newIntersectedFigureConstraint = new Rectangle(old.x, old.y + intersection.height + SizeConstants.TOP_LEVEL_VERTICAL_GAP, old.width, old.height);
					setConstraint(siblingFigure, newIntersectedFigureConstraint);
					resizeSiblings(siblingPart);
				}
			}
		}
	}
	
	public Rectangle getDefaultConstraint(EditPart part){
		return utils.getDefaultConstraint(part);
	}
}
