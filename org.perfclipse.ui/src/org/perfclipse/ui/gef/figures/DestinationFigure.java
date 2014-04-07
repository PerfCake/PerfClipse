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

import org.eclipse.swt.graphics.Color;

/**
 * @author Jakub Knetl
 *
 */
public class DestinationFigure extends LabeledRoundedRectangle implements
		ISwitchable {

	SwitchFigure switchFigure;
	public DestinationFigure(String name, Color foregroundColor,
			Color backgroundColor) {
		super(name, foregroundColor, backgroundColor);

		switchFigure = new SwitchFigure();
//		switchFigure.setPreferredSize(7,7);
		add(switchFigure, 0);
	}

	/* (non-Javadoc)
	 * @see org.perfclipse.ui.gef.figures.ISwitchable#setSwitch(boolean)
	 */
	@Override
	public void setSwitch(boolean enabled) {
		switchFigure.setSwitch(enabled);
	}

}
