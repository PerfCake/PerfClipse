/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2013 Jakub Knetl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.perfclipse.wizards.swt.events;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;

/**
 * Class which maps mouse double click MouseListener event to 
 * {@link SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)}
 * @author Jakub Knetl
 *
 */
public class DoubleClickSelectionAdapter extends MouseAdapter {

	private SelectionAdapter adapter;

	/**
	 * Creates new instance of {@link DoubleClickSelectionAdapter} which will 
	 * use selection adapter to handle double click.
	 * @param adapter adapter which will be invoked on doubleClick
	 */
	public DoubleClickSelectionAdapter(SelectionAdapter adapter) {
		if (adapter == null){
			throw new IllegalArgumentException("adapter is null");
		}
		this.adapter = adapter;
	}

	/**
	 * Converts {@link MouseEvent} to {@link SelectionEvent} and calls 
	 * {@link SelectionAdapter#widgetSelected(SelectionEvent)} on adapter.
	 */
	@Override
	public void mouseDoubleClick(MouseEvent e) {
		SelectionEvent selEvent = new SelectionEvent(mouseEventCopy(e));

		adapter.widgetSelected(selEvent);
	}
	
	/**
	 * Copies necessary data from mouse event into event.
	 * 
	 * TODO:
	 * Current implementation of widgetSelected method does not use
	 * the argument so it could basically be null, but it should be 
	 * fixed.
	 * 
	 * @param mouseEvent event from which data will be copied
	 * @return event event object representing mouse event
	 */
	private Event mouseEventCopy(MouseEvent mouseEvent){
		Event event = new Event();
		//TODO: copy ALL data from MouseEvent to selection event
		event.widget = mouseEvent.widget;
		
		return event;
	}
	

}
