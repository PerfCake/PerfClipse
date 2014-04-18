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


import org.eclipse.swt.graphics.Color;

/**
 * @author Jakub Knetl
 *
 */
public class ReporterRectangle extends TwoPartRectangle implements ISwitchable {

	private SwitchFigure switchFigure;

	public ReporterRectangle(String name, Color foregroundColor,
			Color backgroundColor) {
		super(name, foregroundColor, backgroundColor);

//		// Align in header layer top-left
//		FlowLayout layout = new FlowLayout();
//		layout.setMajorAlignment(OrderedLayout.ALIGN_TOPLEFT);
//		getHeaderLayer().setLayoutManager(layout);

		switchFigure = new SwitchFigure();
//		switchFigure.setPreferredSize(11,11);

		getHeaderLayer().add(switchFigure, 0);
	}
	
	
	@Override
	public void setSwitch(boolean enabled){
		switchFigure.setSwitch(enabled);
	}


	public SwitchFigure getSwitchFigure() {
		return switchFigure;
	}
}
