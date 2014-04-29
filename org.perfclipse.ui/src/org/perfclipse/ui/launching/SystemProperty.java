package org.perfclipse.ui.launching;

/**
 * Represents PerfCake system property
 * @author Jakub Knetl
 *
 */
public class SystemProperty{
	private String name;
	private String value;
	/**
	 * @param name
	 * @param value
	 */
	public SystemProperty(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	/**
	 * 
	 */
	public SystemProperty() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}