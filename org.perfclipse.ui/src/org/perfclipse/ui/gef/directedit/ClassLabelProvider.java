package org.perfclipse.ui.gef.directedit;

import org.eclipse.jface.viewers.LabelProvider;

public class ClassLabelProvider extends LabelProvider {
	@Override 
	public String getText(Object element){
		if (element instanceof Class){
			Class<?> clazz = (Class<?>) element;
			return clazz.getSimpleName();
		}
		return null;
	}
}