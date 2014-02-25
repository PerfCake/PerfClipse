package org.perfclipse.ui.gef.directedit;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

public class LabelCellEditorLocator implements CellEditorLocator {

	protected Label label;

	public LabelCellEditorLocator(Label label) {
		this.label = label;
	}

	@Override
	public void relocate(CellEditor celleditor) {
		// TODO Auto-generated method stub
		Text text = (Text) celleditor.getControl();
		
		Rectangle bounds = label.getBounds().getCopy();
		label.translateToAbsolute(bounds);
		Point p = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		text.setBounds(bounds.x, bounds.y, p.x, p.y);
		
		
	}

	public final Label getLabel() {
		return label;
	}
	
}
