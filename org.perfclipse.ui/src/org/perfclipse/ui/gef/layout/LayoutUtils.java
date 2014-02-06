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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.perfclipse.ui.gef.parts.GeneratorEditPart;
import org.perfclipse.ui.gef.parts.MessagesEditPart;
import org.perfclipse.ui.gef.parts.ReportingEditPart;
import org.perfclipse.ui.gef.parts.SenderEditPart;
import org.perfclipse.ui.gef.parts.ValidationEditPart;

public class LayoutUtils {
	
	private int verticalGap = SizeConstants.TOP_LEVEL_VERTICAL_GAP;
	private int horizontalGap = SizeConstants.TOP_LEVEL_HORIZONTAL_GAP;
	
	private int componentWidth = SizeConstants.TOP_LEVEL_RECTANGLE_WIDHT;
//	private int componentHeight = SizeConstants.TOP_LEVEL_RECTANGLE_HEIGHT;
	private int componentHeight = 100;
	
	private static LayoutUtils instance;
	
	private LayoutUtils(){}
	
	public Rectangle getDefaultConstraint(EditPart child){
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;

		if (child instanceof GeneratorEditPart){
			x = 0;
			y = 0;
			width = 2*componentWidth;
			height = componentHeight;
		}
		if (child instanceof SenderEditPart){
			x = 0;
			y = verticalGap + componentHeight;
			width = 2*componentWidth;
			height = componentHeight;
		}
		if (child instanceof MessagesEditPart){
			x = 0;
			y = 2*verticalGap + 2*componentHeight;
			width = componentWidth;
			height = componentHeight;
		}
		if (child instanceof ValidationEditPart){
			x = 0;
			y = 3*verticalGap + 3*componentHeight;
			width = componentWidth;
			height = componentHeight;
		}
		if (child instanceof ReportingEditPart){
			x = horizontalGap + componentWidth;
			y = 2*verticalGap + 2*componentHeight;
			width = componentWidth - horizontalGap;
			height = 2*componentHeight;
		}
			
		//if child rectangle is not known then return null
		if (x == 0 && y == 0 && width == 0 && height == 0){
			return null;
		}

		return new Rectangle(x, y, width, height);
	}
	
	public static LayoutUtils getInstance(){
		if (instance == null){
			instance = new LayoutUtils();
		}
		
		return instance;
	}
	
	
}
