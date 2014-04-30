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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;

/**
 * Event that maps Delete key pressed event to {@link SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)}
 * method
 * @author Jakub Knetl
 *
 */
public class DelKeyPressedSelectionAdapter extends KeyAdapter {

	private SelectionAdapter adapter;

	/**
	 * 
	 */
	public DelKeyPressedSelectionAdapter(SelectionAdapter adapter) {
		if (adapter == null){
			throw new IllegalArgumentException("adapter is null");
		}
		this.adapter = adapter;
	}

	/**
	 * Converts del key released event to {@link SelectionAdapter#widgetSelected(SelectionEvent)}
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.keyCode == SWT.DEL){
			SelectionEvent selEvent = new SelectionEvent(KeyEventCopy(e));
			
			adapter.widgetSelected(selEvent);
		}
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
	private Event KeyEventCopy(KeyEvent e) {
		Event event = new Event();
		//TODO: copy ALL data from KeyEvent to selection event
		event.widget = e.widget;
		
		return event;
	}
	
	

}
