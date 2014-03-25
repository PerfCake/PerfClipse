package org.perfclipse.ui.gef.layout.colors;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.ui.Activator;
import org.perfclipse.ui.gef.parts.DestinationEditPart;
import org.perfclipse.ui.gef.parts.GeneratorEditPart;
import org.perfclipse.ui.gef.parts.MessageEditPart;
import org.perfclipse.ui.gef.parts.MessagesEditPart;
import org.perfclipse.ui.gef.parts.PropertiesEditPart;
import org.perfclipse.ui.gef.parts.PropertyEditPart;
import org.perfclipse.ui.gef.parts.ReporterEditPart;
import org.perfclipse.ui.gef.parts.ReportingEditPart;
import org.perfclipse.ui.gef.parts.ScenarioEditPart;
import org.perfclipse.ui.gef.parts.SenderEditPart;
import org.perfclipse.ui.gef.parts.ValidationEditPart;
import org.perfclipse.ui.gef.parts.ValidatorEditPart;
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
		if (part instanceof ValidatorEditPart){
			return getColorFromPreference(PreferencesConstants.VALIDATOR_COLOR_FOREGROUND);
		}
		if (part instanceof MessagesEditPart){
			return getColorFromPreference(PreferencesConstants.MESSAGES_COLOR_FOREGROUND);
		}
		if (part instanceof MessageEditPart){
			return getColorFromPreference(PreferencesConstants.MESSAGE_COLOR_FOREGROUND);
		}
		if (part instanceof ReportingEditPart){
			return getColorFromPreference(PreferencesConstants.REPORTING_COLOR_FOREGROUND);
		}
		if (part instanceof ReporterEditPart){
			return getColorFromPreference(PreferencesConstants.REPORTER_COLOR_FOREGROUND);
		}
		if (part instanceof DestinationEditPart){
			return getColorFromPreference(PreferencesConstants.DESTINATION_COLOR_FOREGROUND);
		}
		if (part instanceof PropertiesEditPart){
			return getColorFromPreference(PreferencesConstants.PROPERTIES_COLOR_FOREGROUND);
		}
		if (part instanceof PropertyEditPart){
			return getColorFromPreference(PreferencesConstants.PROPERTY_COLOR_FOREGROUND);
		}
		return null;
	}
	
	public Color getBackgroundColor(EditPart part){
		if (part instanceof ScenarioEditPart){
			return getColorFromPreference(PreferencesConstants.SCENARIO_COLOR_BACKGROUND);
		}
		if (part instanceof GeneratorEditPart){
			return getColorFromPreference(PreferencesConstants.GENERATOR_COLOR_BACKGROUND);
		}
		if (part instanceof SenderEditPart){
			return getColorFromPreference(PreferencesConstants.SENDER_COLOR_BACKGROUND);
		}
		if (part instanceof ValidationEditPart){
			return getColorFromPreference(PreferencesConstants.VALIDATION_COLOR_BACKGROUND);
		}
		if (part instanceof ValidatorEditPart){
			return getColorFromPreference(PreferencesConstants.VALIDATOR_COLOR_BACKGROUND);
		}
		if (part instanceof MessagesEditPart){
			return getColorFromPreference(PreferencesConstants.MESSAGES_COLOR_BACKGROUND);
		}
		if (part instanceof MessageEditPart){
			return getColorFromPreference(PreferencesConstants.MESSAGE_COLOR_BACKGROUND);
		}
		if (part instanceof ReportingEditPart){
			return getColorFromPreference(PreferencesConstants.REPORTING_COLOR_BACKGROUND);
		}
		if (part instanceof ReporterEditPart){
			return getColorFromPreference(PreferencesConstants.REPORTER_COLOR_BACKGROUND);
		}
		if (part instanceof DestinationEditPart){
			return getColorFromPreference(PreferencesConstants.DESTINATION_COLOR_BACKGROUND);
		}
		if (part instanceof PropertiesEditPart){
			return getColorFromPreference(PreferencesConstants.PROPERTIES_COLOR_BACKGROUND);
		}
		if (part instanceof PropertyEditPart){
			return getColorFromPreference(PreferencesConstants.PROPERTY_COLOR_BACKGROUND);
		}
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
