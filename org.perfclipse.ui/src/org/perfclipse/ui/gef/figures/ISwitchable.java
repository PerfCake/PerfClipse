package org.perfclipse.ui.gef.figures;

/**
 * Represents Objects which can be switched on/off
 * @author Jakub Knetl
 *
 */
public interface ISwitchable {

	/**
	 * Switches figure to enabled attribute
	 * @param enabled
	 */
	public abstract void setSwitch(boolean enabled);

}