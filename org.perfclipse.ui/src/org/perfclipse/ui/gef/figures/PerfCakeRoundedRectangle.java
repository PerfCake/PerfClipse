package org.perfclipse.ui.gef.figures;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;

public class PerfCakeRoundedRectangle extends RoundedRectangle{

	private static final org.eclipse.draw2d.geometry.Insets CLIENT_AREA_INSETS = new org.eclipse.draw2d.geometry.Insets(10, 10, 21, 21);

	public PerfCakeRoundedRectangle(String name){
		super();
		FlowLayout layout = new FlowLayout(true);
		setLayoutManager(layout);
		setSize(300, 40);
		setPreferredSize(300, 40);
	}
	@Override
	public Rectangle getClientArea(Rectangle rect){
		Rectangle clientArea = super.getClientArea(rect);
		clientArea.shrink(CLIENT_AREA_INSETS);
		return clientArea;
	}
	
	
	
	

}
