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

package org.perfclipse.gef.figures;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.swt.graphics.Color;

public class PerfCakeRoundedRectangle extends RoundedRectangle{

	private static final int TOP_PADDING = 10;
	private static final int LEFT_PADDING = 10; 
	private static final int BOTTOM_PADDING = 10;
	private static final int RIGHT_PADDING = 10;

	private static final org.eclipse.draw2d.geometry.Insets CLIENT_AREA_INSETS = new org.eclipse.draw2d.geometry.Insets(TOP_PADDING, LEFT_PADDING, BOTTOM_PADDING, RIGHT_PADDING);
	

	public PerfCakeRoundedRectangle(String name, Color foregroundColor, Color backgroundColor){
		super();
		if (foregroundColor != null)
			setForegroundColor(foregroundColor);
		if (backgroundColor != null)
			setBackgroundColor(backgroundColor);
		FlowLayout layout = new FlowLayout(true);
		setLayoutManager(layout);
	}
	
	@Override
	public Insets getInsets() {

		return CLIENT_AREA_INSETS;
	}
	
	

	
}
