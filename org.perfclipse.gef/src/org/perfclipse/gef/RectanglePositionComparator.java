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

package org.perfclipse.gef;

import java.util.Comparator;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class RectanglePositionComparator implements Comparator<Rectangle> {

	/**
	 * Compares two rectangle and returns which is positioned more top on the screen.
	 * 
	 * @return Returns positive if first rectangle should be more top,
	 * zero if the y coordinate is same and negative if the first rectangle
	 * is positioned more bottom than second.
	 */
	@Override
	public int compare(Rectangle r1, Rectangle r2) {
		if (r1 == null){
			throw new NullPointerException("Rectangle r1 is null.");
		}
		if (r2 == null){
			throw new NullPointerException("Rectangle r2 is null.");
		}
		
		Point p1 = r1.getTopLeft();
		Point p2 = r2.getTopLeft();
		
		if (p1.y < p2.y){
			return (p2.y - p1.y);
		}
		if (p1.y > p2.y){
			return (p2.y - p1.y);
		}
		return 0;
	}

}
