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


import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Color;

public class TwoPartRectangle extends PerfCakeRoundedRectangle implements ILabeledFigure {
	
	private static final int SECTION_SPACING = 8;

	private Figure headerLayer;
	private Figure contentLayer;
	private Label headerLabel;

	public TwoPartRectangle(String name, 
			Color foregroundColor, Color backgroundColor)
	{
		super(name, foregroundColor, backgroundColor);
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
		layout.setSpacing(SECTION_SPACING);
		setLayoutManager(layout);
		
		headerLayer = new Figure();
		contentLayer = new Figure();

		//TODO: Minimum size has no effect
		Dimension d = new Dimension(10, 100);
		contentLayer.setMinimumSize(d);

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
