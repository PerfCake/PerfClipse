package org.perfclipse.ui.gef.layout.colors;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.ui.gef.parts.GeneratorEditPart;
import org.perfclipse.ui.gef.parts.MessagesEditPart;
import org.perfclipse.ui.gef.parts.ReportingEditPart;
import org.perfclipse.ui.gef.parts.SenderEditPart;
import org.perfclipse.ui.gef.parts.ValidationEditPart;

public class ColorUtils {

	private static ColorUtils instance;

	private ColorUtils()
	{}
	
	public Color getForegroundColor(EditPart part){
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (part instanceof GeneratorEditPart){
			return ColorConstants.blue;
		}
		
		if (part instanceof SenderEditPart){
			return ColorConstants.darkGreen;
		}
		
		if (part instanceof ValidationEditPart){
			return ColorConstants.red;
		}
		if (part instanceof MessagesEditPart){
			return new Color(display, new RGB(138, 43, 226));
		}
		if (part instanceof ReportingEditPart){
			return new Color(display, new RGB(107, 66, 38));
		}
		return null;
	}
	
	public Color getBackgroundColor(EditPart part){
		
		return null;
	}

	public static ColorUtils getInstance(){
		if (instance == null){
			instance = new ColorUtils();
		}
		
		return instance;
	}
	
	
}
