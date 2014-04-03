package org.perfclipse.ui.gef.layout.colors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.ui.Activator;

/**
 * Sigleton class which is able to parse colors for components from eclipse 
 * preferences store.
 * 
 * Created colors are stored in a map and can be disposed by calling {@link ColorUtils#dispose()} method.
 * 
 * @author Jakub Knetl
 */
public class ColorUtils {

	private static ColorUtils instance;
	
	private Map<RGB, Color> map;

	private ColorUtils()
	{
		map = new HashMap<>();
	}
	
	/**
	 * Obtain color from the PerfClipse preferences. If Color with same rgb was
	 * created before than it is reused. It implies that clients should not
	 *  dispose this color since it can be used by other components.
	 * 
	 * @param key Key of preference
	 * @return Color instance for given component or null if the key cannot be 
	 * found in preferences store or value for the given key cannot be parsed to
	 * color.
	 */
	public Color getColor(String key){
		Display display = PlatformUI.getWorkbench().getDisplay();
		IPreferenceStore prefsStore = Activator.getDefault().getPreferenceStore();
		String color = prefsStore.getString(key);
		RGB rgb = parseRGB(color);
		if (rgb != null){
			Color c = map.get(rgb);
			
			if (c == null){
				//puts rgb to map so color could be disposed
				c = new Color(display, rgb);
				map.put(rgb, c);
			}

			return c;
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
	
	/**
	 * Disposes all Color instances which was created using getColor() method.
	 * It also empty map of colors.
	 */
	public void dispose(){
		for (Color c : map.values()){
			c.dispose();
		}
		map.clear();
	}
}
