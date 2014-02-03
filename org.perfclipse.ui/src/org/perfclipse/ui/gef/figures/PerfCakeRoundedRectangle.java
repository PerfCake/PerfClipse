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

package org.perfclipse.ui.gef.figures;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class PerfCakeRoundedRectangle extends RoundedRectangle{

	public static final int TOP_PADDING = 10;
	public static final int LEFT_PADDING = 10; 
	public static final int BOTTOM_PADDING = 10;
	public static final int RIGHT_PADDING = 10;

	private static final org.eclipse.draw2d.geometry.Insets CLIENT_AREA_INSETS = new org.eclipse.draw2d.geometry.Insets(TOP_PADDING, LEFT_PADDING, BOTTOM_PADDING, RIGHT_PADDING);
	

	public PerfCakeRoundedRectangle(String name){
		super();
		FlowLayout layout = new FlowLayout(true);
		setLayoutManager(layout);
	}
	@Override
	public Rectangle getClientArea(Rectangle rect){
		Rectangle clientArea = super.getClientArea(rect);
		clientArea.shrink(CLIENT_AREA_INSETS);
		return clientArea;
	}
	
	@Override
	public Dimension getPreferredSize(int wHint, int hHint){
		Dimension d = super.getPreferredSize(wHint, hHint).getCopy();
		d.expand(new Dimension(LEFT_PADDING + RIGHT_PADDING, TOP_PADDING + BOTTOM_PADDING));
		return d;
	}

	protected static org.eclipse.draw2d.geometry.Insets getClientAreaInsets() {
		return CLIENT_AREA_INSETS;
	}
}
