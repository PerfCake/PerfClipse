package org.perfclipse.ui.gef.layout.colors;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.ui.Activator;
import org.perfclipse.ui.gef.parts.GeneratorEditPart;
import org.perfclipse.ui.gef.parts.MessagesEditPart;
import org.perfclipse.ui.gef.parts.PropertiesEditPart;
import org.perfclipse.ui.gef.parts.ReportingEditPart;
import org.perfclipse.ui.gef.parts.SenderEditPart;
import org.perfclipse.ui.gef.parts.ValidationEditPart;
import org.perfclipse.ui.preferences.PreferencesConstants;

public class ColorUtils {

	private static ColorUtils instance;

	private ColorUtils()
	{}
	
	public Color getForegroundColor(EditPart part){
		if (part instanceof GeneratorEditPart){
			return getColorFromPreference(PreferencesConstants.GENERATOR_COLOR_FOREGROUND);
		}
		if (part instanceof SenderEditPart){
			return getColorFromPreference(PreferencesConstants.SENDER_COLOR_FOREGROUND);
		}
		if (part instanceof ValidationEditPart){
			return getColorFromPreference(PreferencesConstants.VALIDATION_COLOR_FOREGROUND);
		}
		if (part instanceof MessagesEditPart){
			return getColorFromPreference(PreferencesConstants.MESSAGES_COLOR_FOREGROUND);
		}
		if (part instanceof ReportingEditPart){
			return getColorFromPreference(PreferencesConstants.REPORTING_COLOR_FOREGROUND);
		}
		if (part instanceof PropertiesEditPart){
			return getColorFromPreference(PreferencesConstants.PROPERTIES_COLOR_FOREGROUND);
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
	
	protected RGB parseRGB(String rgb){
		if (rgb == null){
			return null;
		}
		
		rgb = rgb.trim();
		String[] parts = rgb.split(",");
		if (parts.length != 3){
			return null;
		}
		
		try{
			int r = Integer.parseInt(parts[0]);
			int g = Integer.parseInt(parts[1]);
			int b = Integer.parseInt(parts[2]);
			return new RGB(r, g, b); 
		} catch (NumberFormatException e){
		}
		
		return null;
		
	}
	
	private Color getColorFromPreference(String key){
		Display display = PlatformUI.getWorkbench().getDisplay();
		IPreferenceStore prefsStore = Activator.getDefault().getPreferenceStore();
		String color = prefsStore.getString(key);
		RGB rgb = parseRGB(color);
		if (rgb != null)
			return new Color(display, rgb);
		
		return null;
	}
	
	
}
