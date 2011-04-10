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
import javax.swing.SpinnerNumberModel;

import java.awt.Insets;
import javax.swing.JLabel;

import configurations.AudioSettings;
import configurations.CaptureSettings;

import recording.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JTextField;
import javax.swing.JRadioButton;

import com.cattura.packet_multibroadcaster.constants.AudioVideoTypes;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

/**
 * GUI for the screencasting software.
 * 
 * @author Petri Tuononen
 *
 */
public class JcasterGUI {

	//global variables
	private JFrame frmJcaster;
	private static Record record;
	private JButton btnRecord;
	private JButton btnPause;
	private JButton btnStop;
	private JButton btnBrowse;
	private JTextField saveLocTextField;
	private JTextField filenameTextField;
	private JRadioButton rdbtnMp;
	private JRadioButton rdbtnAvi;
	private JRadioButton rdbtnMov;
	private JRadioButton rdbtnFlv;
	private JRadioButton rdbtnAudiovideo;
	private JRadioButton rdbtnVideoOnly;
	private JRadioButton rdbtnAudioOnly;
	private JRadioButton rdbtnbit;
	private JRadioButton rdbtnbit_1;
	private ButtonGroup extensionBtngroup;
	private ButtonGroup audioVideoTypeBtnGroup;
	private JTextField txtCountdown;
	private JTextField txtRecordDuration;
	private JTextField txtAudiochannels;
	private JSpinner spinner;
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
		
		btnRecord = new JButton("Record");
		btnPause = new JButton("Pause");
		btnStop = new JButton("Stop");
		
		btnStop.setEnabled(false);
		btnPause.setEnabled(false);
		panel_3.setLayout(new GridLayout(0, 3, 0, 0));
		panel_3.add(btnRecord);
		panel_3.add(btnPause);
		panel_3.add(btnStop);
		
		btnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				record();
			}
		});
		
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO: Implement pause button action listener
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		
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
		gbl_panel_2.rowHeights = new int[]{0, 20, 0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblNoteOnlyRecord = new JLabel("NOTE: ONLY RECORD ONE FILE AND RESTART IF NEED MORE (BUG)");
		GridBagConstraints gbc_lblNoteOnlyRecord = new GridBagConstraints();
		gbc_lblNoteOnlyRecord.gridwidth = 4;
		gbc_lblNoteOnlyRecord.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoteOnlyRecord.gridx = 0;
		gbc_lblNoteOnlyRecord.gridy = 0;
		panel_2.add(lblNoteOnlyRecord, gbc_lblNoteOnlyRecord);
		
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
		gbc_saveLocTextField.gridwidth = 2;
		gbc_saveLocTextField.insets = new Insets(0, 0, 5, 5);
		gbc_saveLocTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveLocTextField.gridx = 1;
		gbc_saveLocTextField.gridy = 2;
		panel_2.add(saveLocTextField, gbc_saveLocTextField);
		saveLocTextField.setColumns(10);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseActionPerformed();
			}
		});
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.anchor = GridBagConstraints.WEST;
		gbc_btnBrowse.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrowse.gridx = 3;
		gbc_btnBrowse.gridy = 2;
		panel_2.add(btnBrowse, gbc_btnBrowse);
		
		rdbtnAudiovideo = new JRadioButton("audio+video");
		GridBagConstraints gbc_rdbtnAudiovideo = new GridBagConstraints();
		gbc_rdbtnAudiovideo.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAudiovideo.gridx = 0;
		gbc_rdbtnAudiovideo.gridy = 3;
		panel_2.add(rdbtnAudiovideo, gbc_rdbtnAudiovideo);
		rdbtnAudiovideo.setSelected(true); //default
		
		rdbtnVideoOnly = new JRadioButton("video only");
		GridBagConstraints gbc_rdbtnVideoOnly = new GridBagConstraints();
		gbc_rdbtnVideoOnly.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnVideoOnly.gridx = 1;
		gbc_rdbtnVideoOnly.gridy = 3;
		panel_2.add(rdbtnVideoOnly, gbc_rdbtnVideoOnly);
		
		rdbtnAudioOnly = new JRadioButton("audio only");
		GridBagConstraints gbc_rdbtnAudioOnly = new GridBagConstraints();
		gbc_rdbtnAudioOnly.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAudioOnly.gridx = 2;
		gbc_rdbtnAudioOnly.gridy = 3;
		panel_2.add(rdbtnAudioOnly, gbc_rdbtnAudioOnly);
		
		audioVideoTypeBtnGroup = new ButtonGroup();
		audioVideoTypeBtnGroup.add(rdbtnAudiovideo);
		audioVideoTypeBtnGroup.add(rdbtnVideoOnly);
		audioVideoTypeBtnGroup.add(rdbtnAudioOnly);
		
		JLabel lblRecordingCountdown = new JLabel("Recording countdown");
		GridBagConstraints gbc_lblRecordingCountdown = new GridBagConstraints();
		gbc_lblRecordingCountdown.anchor = GridBagConstraints.EAST;
		gbc_lblRecordingCountdown.insets = new Insets(0, 0, 5, 5);
		gbc_lblRecordingCountdown.gridx = 0;
		gbc_lblRecordingCountdown.gridy = 4;
		panel_2.add(lblRecordingCountdown, gbc_lblRecordingCountdown);
		
		txtCountdown = new JTextField();
		txtCountdown.setToolTipText("Countdown before recording starts");
		txtCountdown.setText("0");
		GridBagConstraints gbc_txtCountdown = new GridBagConstraints();
		gbc_txtCountdown.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCountdown.insets = new Insets(0, 0, 5, 5);
		gbc_txtCountdown.gridx = 1;
		gbc_txtCountdown.gridy = 4;
		panel_2.add(txtCountdown, gbc_txtCountdown);
		txtCountdown.setColumns(5);
		
		JLabel lblNewLabel = new JLabel("record starts when countdown hits 0");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 4;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblDurationOfRecord = new JLabel("Duration of record");
		GridBagConstraints gbc_lblDurationOfRecord = new GridBagConstraints();
		gbc_lblDurationOfRecord.anchor = GridBagConstraints.EAST;
		gbc_lblDurationOfRecord.insets = new Insets(0, 0, 5, 5);
		gbc_lblDurationOfRecord.gridx = 0;
		gbc_lblDurationOfRecord.gridy = 5;
		panel_2.add(lblDurationOfRecord, gbc_lblDurationOfRecord);
		
		txtRecordDuration = new JTextField();
		txtRecordDuration.setText("0");
		GridBagConstraints gbc_txtRecordDuration = new GridBagConstraints();
		gbc_txtRecordDuration.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRecordDuration.insets = new Insets(0, 0, 5, 5);
		gbc_txtRecordDuration.gridx = 1;
		gbc_txtRecordDuration.gridy = 5;
		panel_2.add(txtRecordDuration, gbc_txtRecordDuration);
		txtRecordDuration.setColumns(5);
		
		JLabel lbluntilStopButton = new JLabel("0 = records until stop button is pressed");
		GridBagConstraints gbc_lbluntilStopButton = new GridBagConstraints();
		gbc_lbluntilStopButton.insets = new Insets(0, 0, 5, 0);
		gbc_lbluntilStopButton.anchor = GridBagConstraints.WEST;
		gbc_lbluntilStopButton.gridwidth = 2;
		gbc_lbluntilStopButton.gridx = 2;
		gbc_lbluntilStopButton.gridy = 5;
		panel_2.add(lbluntilStopButton, gbc_lbluntilStopButton);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Video settings", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{20, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
	    
	    rdbtnAvi = new JRadioButton("avi");
	    GridBagConstraints gbc_rdbtnAvi = new GridBagConstraints();
	    gbc_rdbtnAvi.insets = new Insets(0, 0, 0, 5);
	    gbc_rdbtnAvi.gridx = 2;
	    gbc_rdbtnAvi.gridy = 1;
	    panel.add(rdbtnAvi, gbc_rdbtnAvi);
	    rdbtnAvi.setEnabled(false); //TODO: make avi extension working

	    rdbtnMov = new JRadioButton("mov");
	    GridBagConstraints gbc_rdbtnMov = new GridBagConstraints();
	    gbc_rdbtnMov.insets = new Insets(0, 0, 0, 5);
	    gbc_rdbtnMov.gridx = 3;
	    gbc_rdbtnMov.gridy = 1;
	    panel.add(rdbtnMov, gbc_rdbtnMov);

	    rdbtnFlv = new JRadioButton("flv");
	    GridBagConstraints gbc_rdbtnFlv = new GridBagConstraints();
	    gbc_rdbtnFlv.insets = new Insets(0, 0, 0, 5);
	    gbc_rdbtnFlv.gridx = 4;
	    gbc_rdbtnFlv.gridy = 1;
	    panel.add(rdbtnFlv, gbc_rdbtnFlv);
	    rdbtnFlv.setEnabled(false); //TODO: make flv extension working
	    
		//Group the radio buttons.
	    extensionBtngroup = new ButtonGroup();
	    extensionBtngroup.add(rdbtnMp);
	    extensionBtngroup.add(rdbtnAvi);
	    extensionBtngroup.add(rdbtnMov);
	    extensionBtngroup.add(rdbtnFlv);
	    
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Audio settings", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 87, 116, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblChannels = new JLabel("Channels");
		GridBagConstraints gbc_lblChannels = new GridBagConstraints();
		gbc_lblChannels.insets = new Insets(0, 0, 5, 5);
		gbc_lblChannels.anchor = GridBagConstraints.EAST;
		gbc_lblChannels.gridx = 0;
		gbc_lblChannels.gridy = 1;
		panel_1.add(lblChannels, gbc_lblChannels);
		
		txtAudiochannels = new JTextField();
		txtAudiochannels.setText("2");
		GridBagConstraints gbc_txtAudiochannels = new GridBagConstraints();
		gbc_txtAudiochannels.anchor = GridBagConstraints.WEST;
		gbc_txtAudiochannels.insets = new Insets(0, 0, 5, 5);
		gbc_txtAudiochannels.gridx = 1;
		gbc_txtAudiochannels.gridy = 1;
		panel_1.add(txtAudiochannels, gbc_txtAudiochannels);
		txtAudiochannels.setColumns(10);
		
		JLabel lblMono = new JLabel("1 = mono, 2 = stereo");
		GridBagConstraints gbc_lblMono = new GridBagConstraints();
		gbc_lblMono.gridwidth = 2;
		gbc_lblMono.insets = new Insets(0, 0, 5, 0);
		gbc_lblMono.anchor = GridBagConstraints.WEST;
		gbc_lblMono.gridx = 2;
		gbc_lblMono.gridy = 1;
		panel_1.add(lblMono, gbc_lblMono);
		
		JLabel lblSampleSize = new JLabel("Sample size");
		GridBagConstraints gbc_lblSampleSize = new GridBagConstraints();
		gbc_lblSampleSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblSampleSize.gridx = 0;
		gbc_lblSampleSize.gridy = 2;
		panel_1.add(lblSampleSize, gbc_lblSampleSize);
		
		rdbtnbit = new JRadioButton("8-bit");
		GridBagConstraints gbc_rdbtnbit = new GridBagConstraints();
		gbc_rdbtnbit.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnbit.gridx = 1;
		gbc_rdbtnbit.gridy = 2;
		panel_1.add(rdbtnbit, gbc_rdbtnbit);
		
		rdbtnbit_1 = new JRadioButton("16-bit");
		rdbtnbit_1.setSelected(true);
		GridBagConstraints gbc_rdbtnbit_1 = new GridBagConstraints();
		gbc_rdbtnbit_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnbit_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnbit_1.gridx = 2;
		gbc_rdbtnbit_1.gridy = 2;
		panel_1.add(rdbtnbit_1, gbc_rdbtnbit_1);
		
		ButtonGroup sampleSizeBtnGroup = new ButtonGroup();
		sampleSizeBtnGroup.add(rdbtnbit);
		sampleSizeBtnGroup.add(rdbtnbit_1);
		
		JLabel lblSampleRate = new JLabel("Sample rate");
		GridBagConstraints gbc_lblSampleRate = new GridBagConstraints();
		gbc_lblSampleRate.insets = new Insets(0, 0, 0, 5);
		gbc_lblSampleRate.gridx = 0;
		gbc_lblSampleRate.gridy = 3;
		panel_1.add(lblSampleRate, gbc_lblSampleRate);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerListModel(new String[] {"44100", "22050", "16000", "11025", "8000"}));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 3;
		panel_1.add(spinner, gbc_spinner);
		
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
				showHelpActionPerformed();
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
    	//get audio settings
    	
    	AudioSettings audioSettings = getAudioSettings();
		int captureDuration = 0;
		int time = getRecordDurationValue(); //time in seconds
		//check if timed recording is set
		if(time > 0) {
			captureDuration = time*1000; //time in milliseconds
		}
		try {
			String selectedAudioVideoType = getSelectedAudioVideoTypeRadioButtonName();
			if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.AUDIO_AND_VIDEO)) {
				settings = new CaptureSettings(AudioVideoTypes.AUDIO_AND_VIDEO, saveLocTextField.getText(), filenameTextField.getText(), getSelectedExtensionRadioButtonName(), captureDuration);
			} else if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.VIDEO)) {
				settings = new CaptureSettings(AudioVideoTypes.VIDEO, saveLocTextField.getText(), filenameTextField.getText(), getSelectedExtensionRadioButtonName(), captureDuration);
			} else if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.AUDIO)) {
				settings = new CaptureSettings(AudioVideoTypes.AUDIO, saveLocTextField.getText(), filenameTextField.getText(), getSelectedExtensionRadioButtonName(), captureDuration);
			}
			record = new Record(settings, audioSettings);
		} catch (Exception e) {
			System.out.println("Error when trying to configure recording settings.");
		}
    }
    
    /**
     * Get the name of the selected extension type radio button.
     * 
     * @return String
     */
    private String getSelectedExtensionRadioButtonName() {
    	if(rdbtnMp.isSelected()) {
    		return rdbtnMp.getText();
    	}else if(rdbtnAvi.isSelected()) {
    		return rdbtnAvi.getText();
    	} else if(rdbtnMov.isSelected()) {
    		return rdbtnMov.getText();
    	} else if(rdbtnFlv.isSelected()) {
    		return rdbtnFlv.getText();
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

    /**
     * Action for browse button.
     * 
     */
    private void browseActionPerformed() {
    	JFileChooser chooser = new JFileChooser();
    	chooser.setCurrentDirectory(new java.io.File("."));
    	chooser.setDialogTitle("Choose directory");
    	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	chooser.setAcceptAllFileFilterUsed(false);

    	int returnVal = chooser.showOpenDialog(frmJcaster);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		File file = chooser.getSelectedFile();
    			//display in textfield
    			saveLocTextField.setText(file.getAbsolutePath());
    	} else {
    		System.out.println("File access cancelled by user.");
    	}
    } 
    
    /**
     * Record.
     * 
     */
    private void record() {
    	//setup recording settings
    	setupRecording();
    	final int duration = settings.getCaptureDuration();
    	//if countdown enabled
    	int countdown = getCountdownValue(); //time seconds
    	if (countdown>0) {
    		btnRecord.setEnabled(false);
    		countdown *= 1000; //time in milliseconds
    		showCountdownTimer(countdown);
    		(new Timer()).schedule(new TimerTask(){
    			public void run(){
    				//if timer is enabled
    				if (duration>0) {
    					record.startRecording();
    					btnStop.setEnabled(true);
    					//btnPause.setEnabled(true); //TODO: pause button action
    					(new Timer()).schedule(new TimerTask() {
    						public void run () {
    							//stop recording
    							record.stopRecording();
    							btnRecord.setEnabled(true);
    							btnStop.setEnabled(false);
    							btnPause.setEnabled(false);
    						}
    					}, duration);
    				} else { //countdown, no timer
    					//start recording
    					record.startRecording();
    					//stop and pause buttons come visible after countdown has run
    					btnRecord.setEnabled(false);
    					btnStop.setEnabled(true);
    					//btnPause.setEnabled(true); TODO: implement pause action
    					//TODO: Mimimize frame to taskbar
    				}
    			}
    		}, countdown+300); //add 0.3s offset for countdown frame to exit
    	} else { // no countdown
    		//if timer enabled
    		if (duration>0) {
    			record.startRecording();
    			btnRecord.setEnabled(false);
    			btnStop.setEnabled(true);
    			//btnPause.setEnabled(true); TODO: implement pause action
    			(new Timer()).schedule(new TimerTask() {
    				public void run () {
    					//stop recording
    					record.stopRecording();
    					btnRecord.setEnabled(true);
    					btnStop.setEnabled(false);
    					btnPause.setEnabled(false);
    				}
    			}, duration);
    		} else { //no countdown, no timer
    			record.startRecording();
    			btnRecord.setEnabled(false);
    			btnStop.setEnabled(true);
    			//btnPause.setEnabled(true); TODO: implement pause action
    		}
    	}
    }
    
    /**
     * Stop recording.
     * 
     */
    private void stop() {
		//stop recording
		record.stopRecording();
		btnStop.setEnabled(false);
		btnPause.setEnabled(false);
		btnRecord.setEnabled(true);
    }
    
    /**
     * Action for Exit menu item.
     * 
     * @param e ActionEvent
     */
    private void exitActionPerformed(ActionEvent e) {
    	frmJcaster.dispose();
    }
    
	/**
     * 'Show help' menu item pressed.
     * 
     */
    private void showHelpActionPerformed() {
            new Help().toFront();
    }
 
    /**
     * Shows new frame with backward counting numbers.
     * 
     * @param time time in seconds
     */
    private void showCountdownTimer(int time) {
    	Countdown cd = new Countdown(time);
    	cd.launch();
    }
    
    /**
     * Get integer value of the countdown textfield.
     * Drop decimal part.
     * 
     * @return int Countdown value
     */
    private int getCountdownValue() {
    	return (int) Double.parseDouble(txtCountdown.getText());
    }
    
    /**
     * Get integer part of the record duration textfield.
     * Drop decimal part.
     * 
     * @return int record duration value
     */
    private int getRecordDurationValue() {
    	return (int) Double.parseDouble(txtRecordDuration.getText());
    }
    
    /**
     * Get audio settings.
     * 
     * @return AudioSettings
     */
    private AudioSettings getAudioSettings() {
    	//get settings from the audio settings tab
    	int channels = Integer.parseInt(txtAudiochannels.getText());
    	int sampleSize = 0;
    	if (rdbtnbit.isEnabled()) {
    		sampleSize = 8;
    	} else if (rdbtnbit_1.isEnabled()) {
    		sampleSize = 16;
    	}
    	String strObject = (String)spinner.getValue();
    	int sampleRate = Integer.parseInt(strObject);
    	return new AudioSettings(channels, sampleSize, sampleRate);
    }
    
}
