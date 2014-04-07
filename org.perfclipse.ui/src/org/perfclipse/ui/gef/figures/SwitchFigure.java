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

import org.eclipse.draw2d.Ellipse;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @author Jakub Knetl
 *
 */
public class SwitchFigure extends Ellipse implements ISwitchable {


	private static final int ELLIPSE_DIMENSION = 9;

	/**
	 * Constructs new switch figure
	 */
	public SwitchFigure() {
		super();
		setPreferredSize(ELLIPSE_DIMENSION, ELLIPSE_DIMENSION);
	}

	/* (non-Javadoc)
	 * @see org.perfclipse.ui.gef.figures.ISwitchable#setSwitch(boolean)
	 */
	@Override
	public void setSwitch(boolean enabled){
		Color c;
		if (enabled)
			c = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
		else
			c = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		setBackgroundColor(c);
	}

}
