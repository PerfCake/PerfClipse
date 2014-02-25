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


import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;


public class TwoPartRectangle extends PerfCakeRoundedRectangle implements ILabeledFigure {
	
	private static final int SECTION_SPACING = 8;

	private Figure headerLayer;
	private Figure contentLayer;
	private Label headerLabel;

	public TwoPartRectangle(String name, Dimension defaultSize) {
		super(name);
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
		layout.setSpacing(SECTION_SPACING);
		setLayoutManager(layout);
		
		headerLayer = new Figure();
		contentLayer = new Figure();
		
		/* This code was used to set maximum width of content layer, but this
		 * intervention to layout manager caused that layout manager stopped
		 * resizing component automatically. So manual resizing for content 
		 * layer needs to be implemented when the setPreferredSize is used.
		contentLayer.setPreferredSize(defaultSize.width - PerfCakeRoundedRectangle.getClientAreaInsets().getWidth(), 0);
		 */

		FlowLayout headerLayout = new FlowLayout();
		headerLayout.setMajorAlignment(FlowLayout.ALIGN_CENTER);
		FlowLayout contentLayout = new FlowLayout();
		contentLayout.setStretchMinorAxis(true);
		
		headerLayer.setLayoutManager(headerLayout);
		contentLayer.setLayoutManager(contentLayout);
		
		add(headerLayer);
		add(contentLayer);
		
		headerLabel = new Label();
		headerLabel.setText(name);
		headerLayer.add(headerLabel);
		
	}

	public Figure getHeaderLayer() {
		return headerLayer;
	}

	public Figure getContentLayer() {
		return contentLayer;
	}

	@Override
	public final Label getLabel() {
		return headerLabel;
	}
	
	
}
