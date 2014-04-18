/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
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


package org.perfclipse.gef.policies;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public abstract class AbstractListEditPolicy extends OrderedLayoutEditPolicy {

	public AbstractListEditPolicy() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Computes EditPart position.
	 * 
	 * @return EditPart which should be right of selected edit part 
	 * after move.
	 */
	@Override
	protected EditPart getInsertionReference(Request request) {
		if (request instanceof ChangeBoundsRequest){
			List<?> parts = getHost().getChildren();

			if (parts.isEmpty())
				return null;

			ChangeBoundsRequest req = (ChangeBoundsRequest) request;
			Point p = req.getLocation();
			
			for (Object e : parts){
				if (!(e instanceof GraphicalEditPart))
					continue;
	
				Rectangle b = ((GraphicalEditPart) e).getFigure().getBounds();
				
				if (p.y > b.y + b.height)
					continue;
				
				//x coordinate in the middle of the figure
				int middle = b.x + (b.width/2);
				if (p.x >  middle)
					continue;
				
				return (EditPart) e;
			}
		}
		return null;
	}

	/**
	 * Calculates new index after move.
	 * @return new Index position of component. Or negative if move is not neccessary
	 */
	protected int calculateNewIndex(EditPart child, EditPart after) {
		List<?> list = getHost().getChildren();
	
		int newIndex = list.indexOf(after);
		
		if (newIndex < 0)
			newIndex = list.size();
		
		if (newIndex > list.indexOf(child)){
			newIndex--;
		}
	
		if (newIndex < 0)
			return -1;
		if (newIndex == list.indexOf(child))
			return -2;
		
		return newIndex;
		
	}

}
