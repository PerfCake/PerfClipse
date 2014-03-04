package org.perfclipse.ui.gef.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Color;

public class EditableLabel extends Label implements ILabeledFigure {

	public EditableLabel(String s, Color foregroundColor, Color backgroundColor) {
		super(s);
		if (foregroundColor != null)
			setForegroundColor(foregroundColor);
		if (backgroundColor != null)
			setBackgroundColor(backgroundColor);
	}

	@Override
	public Label getLabel() {
		return this;
	}

}
