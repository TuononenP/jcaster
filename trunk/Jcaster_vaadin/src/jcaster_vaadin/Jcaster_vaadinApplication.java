//    Copyright (C) 2011  Petri Tuononen
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.
package jcaster_vaadin;

import com.vaadin.Application;
import com.vaadin.ui.*;

/**
 * Vaadin application launcher.
 * 
 * @author Petri Tuononen
 *
 */
public class Jcaster_vaadinApplication extends Application {

	private static final long serialVersionUID = 847113664237171205L;

	@Override
	public void init() {
		Window mainWindow = new Window("Jcaster");
		mainWindow.addComponent(new Gui2());
		setMainWindow(mainWindow);
	}

}
