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

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JLabel;

import configurations.CaptureSettings;

import recording.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.JRadioButton;

import com.cattura.packet_multibroadcaster.constants.AudioVideoTypes;

public class JcasterGUI {

	private JFrame frmJcaster;
	private static Record record;
	private JButton btnRecord;
	private JButton btnPause;
	private JButton btnStop;
	private JButton btnBrowse;
	private JTextField saveLocTextField;
	private JTextField filenameTextField;
	private JRadioButton rdbtnMp = new JRadioButton();
	private JRadioButton rdbtnMov = new JRadioButton();
	private JRadioButton rdbtnAudiovideo = new JRadioButton();
	private JRadioButton rdbtnVideoOnly = new JRadioButton();
	private JRadioButton rdbtnAudioOnly = new JRadioButton();
	private ButtonGroup extensionBtngroup;
	private ButtonGroup audioVideoTypeBtnGroup;
	private CaptureSettings settings;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JcasterGUI window = new JcasterGUI();
					window.frmJcaster.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JcasterGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJcaster = new JFrame();
		frmJcaster.setTitle("Jcaster");
		frmJcaster.setResizable(false);
		frmJcaster.setBounds(100, 100, 450, 300);
		frmJcaster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJcaster.setLocationRelativeTo(null);
		JFrame.setDefaultLookAndFeelDecorated(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{448, 0};
		gridBagLayout.rowHeights = new int[]{46, 203, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frmJcaster.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		frmJcaster.getContentPane().add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		btnRecord = new JButton("Record");
		btnPause = new JButton("Pause");
		btnStop = new JButton("Stop");
		
		btnStop.setEnabled(false);
		btnPause.setEnabled(false);
		
		GridBagConstraints gbc_btnRecord = new GridBagConstraints();
		gbc_btnRecord.anchor = GridBagConstraints.WEST;
		gbc_btnRecord.fill = GridBagConstraints.VERTICAL;
		gbc_btnRecord.insets = new Insets(0, 0, 0, 5);
		gbc_btnRecord.gridx = 0;
		gbc_btnRecord.gridy = 0;
		panel_3.add(btnRecord, gbc_btnRecord);
		
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.anchor = GridBagConstraints.WEST;
		gbc_btnPause.fill = GridBagConstraints.VERTICAL;
		gbc_btnPause.insets = new Insets(0, 0, 0, 5);
		gbc_btnPause.gridx = 1;
		gbc_btnPause.gridy = 0;
		panel_3.add(btnPause, gbc_btnPause);
		
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.anchor = GridBagConstraints.WEST;
		gbc_btnStop.fill = GridBagConstraints.VERTICAL;
		gbc_btnStop.gridx = 2;
		gbc_btnStop.gridy = 0;
		panel_3.add(btnStop, gbc_btnStop);
		
		//add mouse listeners
		btnRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//setup recording settings
				try {
					setupRecording();
				} catch (Exception e2) {
					System.out.println("Error when trying to setup recording.");
				}
				
				//start recording
				try {
					record.startRecording();
					//TODO: Mimimize frame to taskbar
				} catch (Exception e2) {
					System.out.println("Capture settings must be configured.");
				}
				btnRecord.setEnabled(false);
				btnStop.setEnabled(true);
//				btnPause.setEnabled(true); TODO: implement pause action
			}
		});
		
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//stop recording
				record.stopRecording();
				btnStop.setEnabled(false);
//				btnPause.setEnabled(false); TODO: implement pause action
				btnRecord.setEnabled(true);
			}
		});
		
//		btnBrowse.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				browseActionPerformed(e);
//			}
//		});
		
//		btnBrowse.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				browseActionPerformed();
//			}
//		});
		
		//add mmnemonics
		btnRecord.setMnemonic(KeyEvent.VK_R);
		btnPause.setMnemonic(KeyEvent.VK_P);
		btnStop.setMnemonic(KeyEvent.VK_S);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		frmJcaster.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("General settings", null, panel_2, null);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 20, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblFilename = new JLabel("Filename");
		GridBagConstraints gbc_lblFilename = new GridBagConstraints();
		gbc_lblFilename.anchor = GridBagConstraints.EAST;
		gbc_lblFilename.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilename.gridx = 0;
		gbc_lblFilename.gridy = 1;
		panel_2.add(lblFilename, gbc_lblFilename);
		
		filenameTextField = new JTextField("test");
		GridBagConstraints gbc_filenameTextField = new GridBagConstraints();
		gbc_filenameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_filenameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_filenameTextField.gridx = 1;
		gbc_filenameTextField.gridy = 1;
		panel_2.add(filenameTextField, gbc_filenameTextField);
		filenameTextField.setColumns(10);
		
		JLabel lblSaveLocation = new JLabel("Save location");
		GridBagConstraints gbc_lblSaveLocation = new GridBagConstraints();
		gbc_lblSaveLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaveLocation.anchor = GridBagConstraints.EAST;
		gbc_lblSaveLocation.gridx = 0;
		gbc_lblSaveLocation.gridy = 2;
		panel_2.add(lblSaveLocation, gbc_lblSaveLocation);
		
		saveLocTextField = new JTextField(CaptureSettings.getDefaultOutputDirPath());
		GridBagConstraints gbc_saveLocTextField = new GridBagConstraints();
		gbc_saveLocTextField.insets = new Insets(0, 0, 5, 5);
		gbc_saveLocTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveLocTextField.gridx = 1;
		gbc_saveLocTextField.gridy = 2;
		panel_2.add(saveLocTextField, gbc_saveLocTextField);
		saveLocTextField.setColumns(10);
		
		btnBrowse = new JButton("Browse...");
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowse.gridx = 2;
		gbc_btnBrowse.gridy = 2;
		panel_2.add(btnBrowse, gbc_btnBrowse);
		
		rdbtnAudiovideo = new JRadioButton("audio+video");
		GridBagConstraints gbc_rdbtnAudiovideo = new GridBagConstraints();
		gbc_rdbtnAudiovideo.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnAudiovideo.gridx = 0;
		gbc_rdbtnAudiovideo.gridy = 3;
		panel_2.add(rdbtnAudiovideo, gbc_rdbtnAudiovideo);
		rdbtnAudiovideo.setSelected(true); //default
		
		rdbtnVideoOnly = new JRadioButton("video only");
		GridBagConstraints gbc_rdbtnVideoOnly = new GridBagConstraints();
		gbc_rdbtnVideoOnly.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnVideoOnly.gridx = 1;
		gbc_rdbtnVideoOnly.gridy = 3;
		panel_2.add(rdbtnVideoOnly, gbc_rdbtnVideoOnly);
		
		rdbtnAudioOnly = new JRadioButton("audio only");
		GridBagConstraints gbc_rdbtnAudioOnly = new GridBagConstraints();
		gbc_rdbtnAudioOnly.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnAudioOnly.gridx = 2;
		gbc_rdbtnAudioOnly.gridy = 3;
		panel_2.add(rdbtnAudioOnly, gbc_rdbtnAudioOnly);
		
		audioVideoTypeBtnGroup = new ButtonGroup();
		audioVideoTypeBtnGroup.add(rdbtnAudiovideo);
		audioVideoTypeBtnGroup.add(rdbtnVideoOnly);
		audioVideoTypeBtnGroup.add(rdbtnAudioOnly);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Video settings", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{20, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblFormat = new JLabel("Format");
		GridBagConstraints gbc_lblFormat = new GridBagConstraints();
		gbc_lblFormat.insets = new Insets(0, 0, 0, 5);
		gbc_lblFormat.gridx = 0;
		gbc_lblFormat.gridy = 1;
		panel.add(lblFormat, gbc_lblFormat);
		
		rdbtnMp = new JRadioButton("mp4");
		GridBagConstraints gbc_rdbtnMp = new GridBagConstraints();
		gbc_rdbtnMp.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnMp.gridx = 1;
		gbc_rdbtnMp.gridy = 1;
		panel.add(rdbtnMp, gbc_rdbtnMp);
		//set selected by default
		rdbtnMp.setSelected(true);
		
		rdbtnMov = new JRadioButton("mov");
		GridBagConstraints gbc_rdbtnMov = new GridBagConstraints();
		gbc_rdbtnMov.gridx = 2;
		gbc_rdbtnMov.gridy = 1;
		panel.add(rdbtnMov, gbc_rdbtnMov);
		
		//Group the radio buttons.
	    extensionBtngroup = new ButtonGroup();
	    extensionBtngroup.add(rdbtnMp);
	    extensionBtngroup.add(rdbtnMov);
	    
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Audio settings", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0};
		gbl_panel_1.rowHeights = new int[]{0};
		gbl_panel_1.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JMenuBar menuBar = new JMenuBar();
		frmJcaster.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setMnemonic(KeyEvent.VK_Q);
		mnNewMenu.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitActionPerformed(e);
			}
		});
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setMnemonic(KeyEvent.VK_F11);
		mnHelp.add(mntmHelp);
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHelpActionPerformed(e);
			}
		});
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmHelp.setMnemonic(KeyEvent.VK_A);
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});

	}
	
    /**
     * Setup recording process.
     */
    private void setupRecording() {
    	String selectedAudioVideoType = getSelectedAudioVideoTypeRadioButtonName();
    	if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.AUDIO_AND_VIDEO)) {
    		settings = new CaptureSettings(AudioVideoTypes.AUDIO_AND_VIDEO, saveLocTextField.getText(), filenameTextField.getText(), getSelectedExtensionRadioButtonName());
    	} else if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.VIDEO)) {
    		settings = new CaptureSettings(AudioVideoTypes.VIDEO, saveLocTextField.getText(), filenameTextField.getText(), getSelectedExtensionRadioButtonName());
    	} else if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.AUDIO)) {
    		settings = new CaptureSettings(AudioVideoTypes.AUDIO, saveLocTextField.getText(), filenameTextField.getText(), getSelectedExtensionRadioButtonName());
    	}
    	record = new Record(settings);
    }
    
    private void setupTimedRecording() {
    	settings = new CaptureSettings(AudioVideoTypes.AUDIO_AND_VIDEO, filenameTextField.getName(), getSelectedExtensionRadioButtonName(), saveLocTextField.getText(), 10000);
    	record = new Record(settings);
    }
    
    /**
     * Get the name of the selected extension type radio button.
     * 
     * @return String
     */
    private String getSelectedExtensionRadioButtonName() {
    	if(rdbtnMp.isSelected()) {
    		return rdbtnMp.getText();
    	} else if(rdbtnMov.isSelected()) {
    		return rdbtnMov.getText();
    	}
    	return null;
    }
    
    /**
     * Get the name of the selected audio/video type radio button.
     * 
     * @return String
     */
    private String getSelectedAudioVideoTypeRadioButtonName() {
    	if(rdbtnAudiovideo.isSelected()) {
    		return AudioVideoTypes.AUDIO_AND_VIDEO;
    	} else if(rdbtnVideoOnly.isSelected()) {
    		return AudioVideoTypes.VIDEO;
    	} else if(rdbtnAudioOnly.isSelected()) {
    		return AudioVideoTypes.AUDIO;
    	}
    	return null;
    }
    
    /**
     * Shows about screen.
     */
    private void showAbout() {
    	JOptionPane.showMessageDialog(frmJcaster,
    			"Author: Petri Tuononen\n" +
    			"Date: 4/2011\n" +
    			"Version: 0.1\n" +
    			"GPLv3 license: This software can be used, modified and redistibuted freely.\n" +
    			"No warranties of any kind.");
    }

    private void chooseFileSaveLocation(ActionEvent e) {
    	File file = null;
    	final JFileChooser fc = new JFileChooser();
    	int result = fc.showSaveDialog(frmJcaster);
    	switch (result) { 
    	case JFileChooser.APPROVE_OPTION : 
    		if (fc.getSelectedFile() != null) { // A file was selected
    			file = fc.getSelectedFile();
        		try {
        			//display in textfield
        			saveLocTextField.read(new FileReader(file.getAbsolutePath()), null );
        		} catch (IOException ex) {
        			System.out.println("problem accessing file"+file.getAbsolutePath());
        		}
    		}
    		else { // No file selected 
    			JOptionPane.showMessageDialog(frmJcaster, "No file was selected", "File selection info", JOptionPane.INFORMATION_MESSAGE);
    		}
    		break ; 
    	case JFileChooser.CANCEL_OPTION : // Selection canceled
    		break ; 
    	case JFileChooser.ERROR_OPTION : // An error has occurred 
    		JOptionPane.showMessageDialog(frmJcaster, "An error occured while selecting a file to save", "File selection error", JOptionPane.ERROR_MESSAGE);
    		break ; 
    	} 
    }

    /**
     * Action for browse button.
     * 
     * @param e ActionEvent
     */
    private void browseActionPerformed() {
    	JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showOpenDialog(frmJcaster);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		File file = fc.getSelectedFile();
    		try {
    			//display in textfield
    			saveLocTextField.read(new FileReader(file.getAbsolutePath()), null );
    		} catch (IOException ex) {
    			System.out.println("problem accessing file"+file.getAbsolutePath());
    		}
    	} else {
    		System.out.println("File access cancelled by user.");
    	}
    } 
    
    /**
     * Action for Exit menuitem.
     * 
     * @param e ActionEvent
     */
    private void exitActionPerformed(ActionEvent e) {
    	frmJcaster.dispose();
    }
    
	/**
     * 'Show help' menu item pressed.
     * 
     * @param e ActionEvent
     */
    private void showHelpActionPerformed(ActionEvent e) {
            new Help().toFront();
    }
    
}
