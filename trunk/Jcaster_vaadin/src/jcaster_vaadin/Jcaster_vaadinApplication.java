package jcaster_vaadin;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class Jcaster_vaadinApplication extends Application {

	private static final long serialVersionUID = 847113664237171205L;

	@Override
	public void init() {
		Window mainWindow = new Window("Jcaster");
		mainWindow.addComponent(new Gui2());
		setMainWindow(mainWindow);
	}

}
