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
package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import recording.Record;
import configurations.CaptureSettings;

/**
 * Application class.
 * 
 * @author Petri Tuononen
 *
 */
class Application {
	
	//variables
	private JFrame mainFrame;
	private String title = "Screencast";
	private static Record record;
	//create buttons
	final JButton capture = new JButton("Record");
	final JButton stop = new JButton("Stop");
	
	/**
	 * Creates the application frame, creates menubar,
	 * setups recording settings, initializes buttons
	 * and configures frame.
	 */
	void create() {
		mainFrame = new JFrame(title);
		createMenubar();
		setupRecording();
		initializeButtons();
		View.configureFrame(mainFrame);
	}
	
	/**
	 * Starts the application in a thread-safe manner in an EventQueue.
	 * 
	 * @throws Exception
	 */
	void start() throws Exception {
		if(EventQueue.isDispatchThread())
			create();
		else
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					create();
				}
			});
		mainFrame.setVisible(true);
	}
	
	/**
	 * Launch application.
	 * 
	 * @param title String
	 * @param app Application
	 * @throws Exception
	 */
	static void launch(String title, Application app) throws Exception {
		app.title = title;
		app.start();
	}

	/**
	 * Initializes buttons.
	 */
	void initializeButtons() {
		//initial stage of the buttons
		capture.setEnabled(true);
		stop.setEnabled(false);	
		
		//add action listener to capture button
		ActionListener captureListener = 
			new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				capture.setEnabled(false);
				stop.setEnabled(true);
				//start recording
				record.startRecording();
			}
		};
		capture.addActionListener(captureListener);
		mainFrame.getContentPane().add(capture, BorderLayout.NORTH);

		//add action listener to stop button
		ActionListener stopListener = 
			new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				capture.setEnabled(true);
				stop.setEnabled(false);
				//stop recording
				record.stopRecording();
			}
		};
		stop.addActionListener(stopListener);
		mainFrame.getContentPane().add(stop, BorderLayout.CENTER);
	}
	
	/**
	 * Create menubar.
	 */
	private void createMenubar() {
		// Creates a menubar for a JFrame
		JMenuBar menuBar = new JMenuBar();

		// Add the menubar to the frame
		mainFrame.setJMenuBar(menuBar);

		// Define and add two drop down menu to the menubar
		JMenu fileMenu = new JMenu("File");
		JMenu settingsMenu = new JMenu("Settings");
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(fileMenu);
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem configureAction = new JMenuItem("Configure");
		JMenuItem helpAction = new JMenuItem("Help");
		JMenuItem aboutAction = new JMenuItem("About"); 

		exitAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
			}
		});

		// Add menu items to menus
		fileMenu.add(newAction);
		fileMenu.add(exitAction);
		settingsMenu.add(configureAction);
		helpMenu.add(helpAction);
		helpMenu.add(aboutAction);
	}
	
    /**
     * Setup recording process.
     */
    private void setupRecording() {
    	CaptureSettings settings = new CaptureSettings(); //default settings
    	record = new Record(settings);
    }
	
}

/**
 * View class.
 * 
 * @author Petri Tuononen
 *
 */
class View {
	
	static void configureFrame(JFrame frame) {
		frame.setSize(300, 200);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); //center to the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	static JFrame asApplication(String title, JComponent panel) {
		JFrame mainFrame = new JFrame(title);
		configureFrame(mainFrame);
		mainFrame.getContentPane().add(panel);
		mainFrame.setVisible(true);
		return mainFrame;
	}
	
}

//class SampleComponent extends JLabel {
//	
//	private static final long serialVersionUID = -4364077461560146522L;
//
//	public SampleComponent(String text) {
//		super(text);
//		setOpaque(true);
//	}
//	
//	public static void main(String[] args) {
//		View.asApplication(SampleComponent.class.getName(), new SampleComponent("Hello!"));
//	}
//	
//}
