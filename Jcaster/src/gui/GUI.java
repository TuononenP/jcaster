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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Insets;
import javax.swing.JLabel;

import configurations.AudioSettings;
import configurations.CaptureSettings;

import recording.Record;
import webcam.DisplayWebcamVideo;
import webcam.WebcamDeviceAndDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JTextField;
import javax.swing.JRadioButton;

import com.cattura.packet_multibroadcaster.constants.AudioVideoTypes;

import decode.DecodeVideo;

import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import java.awt.Font;

/**
 * GUI for the screencasting software.
 * 
 * @author Petri Tuononen
 *
 */
public class GUI {

	//global variables
	private JFrame frmJcaster;
	private static Record record;
	private CaptureSettings settings;
	private FileChooser fc;
	
	private JButton btnRecord;
	private JButton btnPause;
	private JButton btnStop;
	private JButton btnBrowse;
	private JTextField saveLocTextField;
	private JTextField filenameTextField;
	private JRadioButton rdbtnMp4;
	private JRadioButton rdbtnAvi;
	private JRadioButton rdbtnMov;
	private JRadioButton rdbtnFlv;
	private JRadioButton rdbtnAudiovideo;
	private JRadioButton rdbtnVideoOnly;
	private JRadioButton rdbtnAudioOnly;
	private JRadioButton rdbtnMp3;
	private JRadioButton rdbtnOggVorbis;
	private JRadioButton rdbtnAac;
	private JRadioButton rdbtnWav;
	private JRadioButton rdbtnBitSize8;
	private JRadioButton rdbtnBitSize16;
	private JRadioButton rdbtnAudioChannelMono;
	private JRadioButton rdbtnAudioChannelStereo;
	private ButtonGroup sampleSizeBtnGroup;
	private ButtonGroup extensionBtngroup;
	private ButtonGroup audioVideoTypeBtnGroup;
	private ButtonGroup audiOChannelBtnGroup;
	private ButtonGroup audioFormatBtnGroup;
	private JTextField txtCountdown;
	private JTextField txtRecordDuration;
	private JSpinner spinner;
	private JLabel lblNoteOnlyRecord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//create a frame
		frmJcaster = new JFrame();

		//configure the frame
		frmJcaster.setTitle("Jcaster");
		frmJcaster.setResizable(false);
		frmJcaster.setBounds(100, 100, 550, 315);
		frmJcaster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJcaster.setLocationRelativeTo(null);
		JFrame.setDefaultLookAndFeelDecorated(false);

		//create a layout for the frame
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{448, 0};
		gridBagLayout.rowHeights = new int[]{46, 203, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};

		//set layout for the frame
		frmJcaster.getContentPane().setLayout(gridBagLayout);

		//create a menubar
		JMenuBar menuBar = new JMenuBar();
		frmJcaster.setJMenuBar(menuBar);

		//create a file menu for the menubar
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		//create a playback menu item for the file menu
		JMenuItem mntmPlayback = new JMenuItem("Playback file");
		mntmPlayback.setMnemonic(KeyEvent.VK_P);
		mnFile.add(mntmPlayback);
		mntmPlayback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playbackMenuItemAction();
			}
		});
		
		//create a transcode menu item for the file menu
		JMenuItem mntmTranscode = new JMenuItem("Transcode");
		mntmTranscode.setMnemonic(KeyEvent.VK_T);
		mnFile.add(mntmTranscode);
		mntmTranscode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transcode();
			}
		});
		
		//create a display webcam menu item for the file menu
		JMenuItem mntmWebcam = new JMenuItem("Display webcam");
		mntmWebcam.setMnemonic(KeyEvent.VK_W);
		mnFile.add(mntmWebcam);
		mntmWebcam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayWebcam();
			}
		});
		
		//create a codec information menu item for the file menu
		JMenuItem mntmCodecInfo = new JMenuItem("Get codec information");
		mntmCodecInfo.setMnemonic(KeyEvent.VK_I);
		mnFile.add(mntmCodecInfo);
		mntmCodecInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCodecInfo();
			}
		});
		
		//create an exit menu item for the file menu
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setMnemonic(KeyEvent.VK_Q);
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitActionPerformed(e);
			}
		});

		//create a help menu for the menubar
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		//create a help menu item for the help menu
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setMnemonic(KeyEvent.VK_F11);
		mnHelp.add(mntmHelp);
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHelpActionPerformed();
			}
		});

		//create an about menu item for the help menu
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmHelp.setMnemonic(KeyEvent.VK_A);
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		
		//create a panel
		JPanel panel_3 = new JPanel();

		//create a layout for the panel
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;

		//create buttons
		btnRecord = new JButton("Record");
		btnPause = new JButton("Pause");
		btnStop = new JButton("Stop");

		//set default enable state for the buttons
		btnRecord.setEnabled(true);
		btnStop.setEnabled(false);
		btnPause.setEnabled(false);

		//add action listener for the record button
		btnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				record();
			}
		});

		//add action listener for the pause button
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO: Implement pause button action listener
			}
		});

		//add action listener for the stop button
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});

		//add mmnemonics
		btnRecord.setMnemonic(KeyEvent.VK_R);
		btnPause.setMnemonic(KeyEvent.VK_P);
		btnStop.setMnemonic(KeyEvent.VK_S);

		//configure panel
		panel_3.setLayout(new GridLayout(0, 3, 0, 0));
		panel_3.add(btnRecord);
		panel_3.add(btnPause);
		panel_3.add(btnStop);

		//add panel to the frame
		frmJcaster.getContentPane().add(panel_3, gbc_panel_3);

		//create a tabbed panel
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;

		//add tabbed panel to the frame
		frmJcaster.getContentPane().add(tabbedPane, gbc_tabbedPane);

		//create a panel for general settings
		JPanel panel_2 = new JPanel();

		//create a layout for the panel
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 20, 0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};

		//set layout for the panel
		panel_2.setLayout(gbl_panel_2);

		//add panel to the tabbed panel
		tabbedPane.addTab("General settings", null, panel_2, null);

		lblNoteOnlyRecord = new JLabel("NOTE: ONLY RECORD ONE FILE AND RESTART IF NEED MORE (BUG)");
		lblNoteOnlyRecord.setFont(new Font("Verdana", Font.PLAIN, 9));
		GridBagConstraints gbc_lblNoteOnlyRecord = new GridBagConstraints();
		gbc_lblNoteOnlyRecord.gridwidth = 4;
		gbc_lblNoteOnlyRecord.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoteOnlyRecord.gridx = 0;
		gbc_lblNoteOnlyRecord.gridy = 0;
		panel_2.add(lblNoteOnlyRecord, gbc_lblNoteOnlyRecord);

		//create filename label
		JLabel lblFilename = new JLabel("Filename");
		GridBagConstraints gbc_lblFilename = new GridBagConstraints();
		gbc_lblFilename.anchor = GridBagConstraints.EAST;
		gbc_lblFilename.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilename.gridx = 0;
		gbc_lblFilename.gridy = 1;
		panel_2.add(lblFilename, gbc_lblFilename);

		//create filename textfield
		filenameTextField = new JTextField("test");
		GridBagConstraints gbc_filenameTextField = new GridBagConstraints();
		gbc_filenameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_filenameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_filenameTextField.gridx = 1;
		gbc_filenameTextField.gridy = 1;
		panel_2.add(filenameTextField, gbc_filenameTextField);
		filenameTextField.setColumns(10);

		//create save location label
		JLabel lblSaveLocation = new JLabel("Save location");
		GridBagConstraints gbc_lblSaveLocation = new GridBagConstraints();
		gbc_lblSaveLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaveLocation.anchor = GridBagConstraints.EAST;
		gbc_lblSaveLocation.gridx = 0;
		gbc_lblSaveLocation.gridy = 2;
		panel_2.add(lblSaveLocation, gbc_lblSaveLocation);

		//create save location textfield
		saveLocTextField = new JTextField(CaptureSettings.getDefaultOutputDirPath());
		GridBagConstraints gbc_saveLocTextField = new GridBagConstraints();
		gbc_saveLocTextField.gridwidth = 2;
		gbc_saveLocTextField.insets = new Insets(0, 0, 5, 5);
		gbc_saveLocTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveLocTextField.gridx = 1;
		gbc_saveLocTextField.gridy = 2;
		panel_2.add(saveLocTextField, gbc_saveLocTextField);
		saveLocTextField.setColumns(10);

		//create action listener for browse button
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseActionPerformed();
			}
		});

		//create a layout for the browse button 
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.anchor = GridBagConstraints.WEST;
		gbc_btnBrowse.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrowse.gridx = 3;
		gbc_btnBrowse.gridy = 2;
		panel_2.add(btnBrowse, gbc_btnBrowse);

		//create audio+video type radio button
		rdbtnAudiovideo = new JRadioButton("audio+video");
		GridBagConstraints gbc_rdbtnAudiovideo = new GridBagConstraints();
		gbc_rdbtnAudiovideo.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAudiovideo.gridx = 0;
		gbc_rdbtnAudiovideo.gridy = 3;
		panel_2.add(rdbtnAudiovideo, gbc_rdbtnAudiovideo);
		rdbtnAudiovideo.setSelected(true); //default

		//create video type radio button
		rdbtnVideoOnly = new JRadioButton("video only");
		GridBagConstraints gbc_rdbtnVideoOnly = new GridBagConstraints();
		gbc_rdbtnVideoOnly.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnVideoOnly.gridx = 1;
		gbc_rdbtnVideoOnly.gridy = 3;
		panel_2.add(rdbtnVideoOnly, gbc_rdbtnVideoOnly);

		//create audio type radiobutton
		rdbtnAudioOnly = new JRadioButton("audio only");
		GridBagConstraints gbc_rdbtnAudioOnly = new GridBagConstraints();
		gbc_rdbtnAudioOnly.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAudioOnly.gridx = 2;
		gbc_rdbtnAudioOnly.gridy = 3;
		panel_2.add(rdbtnAudioOnly, gbc_rdbtnAudioOnly);

		//create a button group for audio&video type
		audioVideoTypeBtnGroup = new ButtonGroup();
		audioVideoTypeBtnGroup.add(rdbtnAudiovideo);
		audioVideoTypeBtnGroup.add(rdbtnVideoOnly);
		audioVideoTypeBtnGroup.add(rdbtnAudioOnly);

		//create recording countdown label
		JLabel lblRecordingCountdown = new JLabel("Recording countdown");
		GridBagConstraints gbc_lblRecordingCountdown = new GridBagConstraints();
		gbc_lblRecordingCountdown.anchor = GridBagConstraints.EAST;
		gbc_lblRecordingCountdown.insets = new Insets(0, 0, 5, 5);
		gbc_lblRecordingCountdown.gridx = 0;
		gbc_lblRecordingCountdown.gridy = 4;
		panel_2.add(lblRecordingCountdown, gbc_lblRecordingCountdown);

		//create recording countdown textfield
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

		//create duration of record label
		JLabel lblDurationOfRecord = new JLabel("Duration of record");
		GridBagConstraints gbc_lblDurationOfRecord = new GridBagConstraints();
		gbc_lblDurationOfRecord.anchor = GridBagConstraints.EAST;
		gbc_lblDurationOfRecord.insets = new Insets(0, 0, 5, 5);
		gbc_lblDurationOfRecord.gridx = 0;
		gbc_lblDurationOfRecord.gridy = 5;
		panel_2.add(lblDurationOfRecord, gbc_lblDurationOfRecord);

		//create duration of record textfield
		txtRecordDuration = new JTextField();
		txtRecordDuration.setText("0");
		GridBagConstraints gbc_txtRecordDuration = new GridBagConstraints();
		gbc_txtRecordDuration.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRecordDuration.insets = new Insets(0, 0, 5, 5);
		gbc_txtRecordDuration.gridx = 1;
		gbc_txtRecordDuration.gridy = 5;
		panel_2.add(txtRecordDuration, gbc_txtRecordDuration);
		txtRecordDuration.setColumns(5);

		//create a panel for video settings
		JPanel panel = new JPanel();
		tabbedPane.addTab("Video settings", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{64, 71, 71, 71, 71, 0, 0};
		gbl_panel.rowHeights = new int[]{20, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		//create a label for video format
		JLabel lblFormat = new JLabel("Format");
		GridBagConstraints gbc_lblFormat = new GridBagConstraints();
		gbc_lblFormat.insets = new Insets(0, 0, 0, 5);
		gbc_lblFormat.gridx = 0;
		gbc_lblFormat.gridy = 1;
		panel.add(lblFormat, gbc_lblFormat);

		//create an mp4 format radio button
		rdbtnMp4 = new JRadioButton("mp4");
		GridBagConstraints gbc_rdbtnMp4 = new GridBagConstraints();
		gbc_rdbtnMp4.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnMp4.gridx = 1;
		gbc_rdbtnMp4.gridy = 1;
		panel.add(rdbtnMp4, gbc_rdbtnMp4);
		//set selected by default
		rdbtnMp4.setSelected(true);

		//create an avi format radio button
		rdbtnAvi = new JRadioButton("avi");
		GridBagConstraints gbc_rdbtnAvi = new GridBagConstraints();
		gbc_rdbtnAvi.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnAvi.gridx = 2;
		gbc_rdbtnAvi.gridy = 1;
		panel.add(rdbtnAvi, gbc_rdbtnAvi);
		rdbtnAvi.setEnabled(false); //TODO: make avi extension working

		//create an mov format radio button
		rdbtnMov = new JRadioButton("mov");
		GridBagConstraints gbc_rdbtnMov = new GridBagConstraints();
		gbc_rdbtnMov.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnMov.gridx = 3;
		gbc_rdbtnMov.gridy = 1;
		panel.add(rdbtnMov, gbc_rdbtnMov);

		//create an flv format radio button
		rdbtnFlv = new JRadioButton("flv");
		GridBagConstraints gbc_rdbtnFlv = new GridBagConstraints();
		gbc_rdbtnFlv.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnFlv.gridx = 4;
		gbc_rdbtnFlv.gridy = 1;
		panel.add(rdbtnFlv, gbc_rdbtnFlv);
		rdbtnFlv.setEnabled(false); //TODO: make flv extension working

		//Group the video format radio buttons.
		extensionBtngroup = new ButtonGroup();
		extensionBtngroup.add(rdbtnMp4);
		extensionBtngroup.add(rdbtnAvi);
		extensionBtngroup.add(rdbtnMov);
		extensionBtngroup.add(rdbtnFlv);

		//create a panel for audio settings
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Audio settings", null, panel_1, null);

		//create a layout for the audio settings panel
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{135, 87, 87, 87, 87, 87, 0, 0};
		gbl_panel_1.rowHeights = new int[]{20, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		//create a sample size button group
		sampleSizeBtnGroup = new ButtonGroup();

		//create a label for audio format
		JLabel lblAudioFormat = new JLabel("Audio format");
		GridBagConstraints gbc_lblAudioFormat = new GridBagConstraints();
		gbc_lblAudioFormat.anchor = GridBagConstraints.EAST;
		gbc_lblAudioFormat.insets = new Insets(0, 0, 5, 5);
		gbc_lblAudioFormat.gridx = 0;
		gbc_lblAudioFormat.gridy = 1;
		panel_1.add(lblAudioFormat, gbc_lblAudioFormat);

		//create an mp3 audio format radio button
		rdbtnMp3 = new JRadioButton("mp3");
		rdbtnMp3.setSelected(true); //set selected by default 
		GridBagConstraints gbc_rdbtnMp3 = new GridBagConstraints();
		gbc_rdbtnMp3.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMp3.gridx = 1;
		gbc_rdbtnMp3.gridy = 1;
		panel_1.add(rdbtnMp3, gbc_rdbtnMp3);

		//create an ogg vorbis audio format radio button
		rdbtnOggVorbis = new JRadioButton("ogg vorbis");
		GridBagConstraints gbc_rdbtnOggVorbis = new GridBagConstraints();
		gbc_rdbtnOggVorbis.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOggVorbis.gridx = 2;
		gbc_rdbtnOggVorbis.gridy = 1;
		panel_1.add(rdbtnOggVorbis, gbc_rdbtnOggVorbis);

		//create an aac audio format radio button
		rdbtnAac = new JRadioButton("aac");
		GridBagConstraints gbc_rdbtnAac = new GridBagConstraints();
		gbc_rdbtnAac.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAac.gridx = 3;
		gbc_rdbtnAac.gridy = 1;
		panel_1.add(rdbtnAac, gbc_rdbtnAac);

		//form a button group for audio formats
		audioFormatBtnGroup = new ButtonGroup();
		audioFormatBtnGroup.add(rdbtnMp3);
		audioFormatBtnGroup.add(rdbtnOggVorbis);
		audioFormatBtnGroup.add(rdbtnAac);

		//create a wav audio format radio button
		rdbtnWav = new JRadioButton("wav");
		GridBagConstraints gbc_rdbtnWav = new GridBagConstraints();
		gbc_rdbtnWav.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnWav.gridx = 4;
		gbc_rdbtnWav.gridy = 1;
		panel_1.add(rdbtnWav, gbc_rdbtnWav);
		audioFormatBtnGroup.add(rdbtnWav);

		//create a label for audio channels
		JLabel lblChannels = new JLabel("Channels");
		GridBagConstraints gbc_lblChannels = new GridBagConstraints();
		gbc_lblChannels.insets = new Insets(0, 0, 5, 5);
		gbc_lblChannels.anchor = GridBagConstraints.EAST;
		gbc_lblChannels.gridx = 0;
		gbc_lblChannels.gridy = 2;
		panel_1.add(lblChannels, gbc_lblChannels);

		//create a mono audio radio button
		rdbtnAudioChannelMono = new JRadioButton("mono");
		GridBagConstraints gbc_rdbtnAudioChannelMono = new GridBagConstraints();
		gbc_rdbtnAudioChannelMono.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAudioChannelMono.gridx = 1;
		gbc_rdbtnAudioChannelMono.gridy = 2;
		panel_1.add(rdbtnAudioChannelMono, gbc_rdbtnAudioChannelMono);

		//create a stereo audio radio button
		rdbtnAudioChannelStereo = new JRadioButton("stereo");
		rdbtnAudioChannelStereo.setSelected(true); //set selected by default
		GridBagConstraints gbc_rdbtnAudioChannelsStereo = new GridBagConstraints();
		gbc_rdbtnAudioChannelsStereo.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAudioChannelsStereo.gridx = 2;
		gbc_rdbtnAudioChannelsStereo.gridy = 2;
		panel_1.add(rdbtnAudioChannelStereo, gbc_rdbtnAudioChannelsStereo);

		//create a button group for audio channels
		audiOChannelBtnGroup = new ButtonGroup();
		audiOChannelBtnGroup.add(rdbtnAudioChannelMono);
		audiOChannelBtnGroup.add(rdbtnAudioChannelStereo);

		//create a label for audio sample size
		JLabel lblSampleSize = new JLabel("Sample size");
		GridBagConstraints gbc_lblSampleSize = new GridBagConstraints();
		gbc_lblSampleSize.anchor = GridBagConstraints.EAST;
		gbc_lblSampleSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblSampleSize.gridx = 0;
		gbc_lblSampleSize.gridy = 3;
		panel_1.add(lblSampleSize, gbc_lblSampleSize);

		//create an 8-bit audio sample size radio button
		rdbtnBitSize8 = new JRadioButton("8-bit");
		GridBagConstraints gbc_rdbtnBitSize8 = new GridBagConstraints();
		gbc_rdbtnBitSize8.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnBitSize8.gridx = 1;
		gbc_rdbtnBitSize8.gridy = 3;
		panel_1.add(rdbtnBitSize8, gbc_rdbtnBitSize8);
		sampleSizeBtnGroup.add(rdbtnBitSize8);

		//create an 16-bit audio sample size radio button
		rdbtnBitSize16 = new JRadioButton("16-bit");
		rdbtnBitSize16.setSelected(true);
		GridBagConstraints gbc_rdbtnBitSize16 = new GridBagConstraints();
		gbc_rdbtnBitSize16.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnBitSize16.gridx = 2;
		gbc_rdbtnBitSize16.gridy = 3;
		panel_1.add(rdbtnBitSize16, gbc_rdbtnBitSize16);
		sampleSizeBtnGroup.add(rdbtnBitSize16);

		//create a label for audio sample rate
		JLabel lblSampleRate = new JLabel("Sample rate");
		lblSampleRate.setToolTipText("use cautiously");
		GridBagConstraints gbc_lblSampleRate = new GridBagConstraints();
		gbc_lblSampleRate.anchor = GridBagConstraints.EAST;
		gbc_lblSampleRate.insets = new Insets(0, 0, 0, 5);
		gbc_lblSampleRate.gridx = 0;
		gbc_lblSampleRate.gridy = 4;
		panel_1.add(lblSampleRate, gbc_lblSampleRate);

		//create a spinner to choose the audio sample rate
		spinner = new JSpinner();
		spinner.setModel(new SpinnerListModel(new String[] {"44100", "22050", "16000", "11025", "8000"}));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.gridwidth = 2;
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 4;
		panel_1.add(spinner, gbc_spinner);
		
		//create a file chooser
		fc = new FileChooser(frmJcaster);
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
			String saveLocation = saveLocTextField.getText();
			String filename = filenameTextField.getText();
			String extension = null;
			if (getSelectedAudioVideoTypeRadioButtonName() == AudioVideoTypes.AUDIO) {
				extension = "mp3";
			} else {
				extension = getSelectedExtensionRadioButtonName();
			}
			String selectedAudioVideoType = getSelectedAudioVideoTypeRadioButtonName();
			if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.AUDIO_AND_VIDEO)) {
				settings = new CaptureSettings(AudioVideoTypes.AUDIO_AND_VIDEO, saveLocation, filename, extension, captureDuration);
			} else if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.VIDEO)) {
				settings = new CaptureSettings(AudioVideoTypes.VIDEO, saveLocation, filename, extension, captureDuration);
			} else if(selectedAudioVideoType.equalsIgnoreCase(AudioVideoTypes.AUDIO)) {
				//override audio format over video format
				extension = getSelectedAudioFormat();
				settings = new CaptureSettings(AudioVideoTypes.AUDIO, saveLocation, filename, extension, captureDuration);
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
		if(rdbtnMp4.isSelected()) {
			return rdbtnMp4.getText();
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
				"Version: 0.2\n" +
				"GPLv3 license: This software can be used, modified and redistibuted freely.\n" +
				"No warranties of any kind.");
	}

	/**
	 * Action for browse button.
	 * 
	 */
	private void browseActionPerformed() {
		String dirPath = fc.getDirectoryPath();
		if (dirPath != null) {
			//display in textfield
			saveLocTextField.setText(dirPath);
		} else {
			//do nothing
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
		playbackWithConfirmDialog();
	}

	/**
	 * Playback (decode) media file.
	 */
	private void playback(String filename) {
		try {
			new DecodeVideo(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Asks if user wants to playback the newly created recording.
	 */
	private void playbackWithConfirmDialog() {
		int response = JOptionPane.showConfirmDialog(frmJcaster,
				"Would you like to playback the file you just recorded?",
				"Playback",
				JOptionPane.YES_NO_OPTION);
		if (response == JOptionPane.YES_OPTION) {
			playback(getFullOutputPath());
		} else if (response == JOptionPane.NO_OPTION) {
			//do nothing
		} else if (response == JOptionPane.CLOSED_OPTION) {
			//do nothing
		}
	}

	/**
	 * Opens a file chooser to select the media file to be decoded.
	 */
	private void playbackMenuItemAction() {
		String filename = fc.getFilePath();
		if (filename != null) {
			playback(filename);
		}
	}
	
	/**
	 * Transcode a file from one format to another.
	 */
	private void transcode() {
		new TranscodeWindow(frmJcaster);
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
	 * Get the selected audio format.
	 * 
	 * @return String
	 */
	private String getSelectedAudioFormat() {
		if(rdbtnMp3.isSelected()) {
			return rdbtnMp3.getText();
		} else if(rdbtnOggVorbis.isSelected()) {
			return "ogg";
		} else if(rdbtnAac.isSelected()) {
			return rdbtnAac.getText();
		} else if(rdbtnWav.isSelected()) {
			return rdbtnWav.getText();
		} else {
			return null;
		}
	}

	/**
	 * Get the selected audio channel count.
	 * 
	 * @return int
	 */
	private int getSelectedAudioChannelCount() {
		if (rdbtnAudioChannelMono.isSelected()) {
			return 1;
		} else if(rdbtnAudioChannelStereo.isSelected()) {
			return 2;
		} else {
			return 0;
		}
	}

	/**
	 * Get selected sample size value.
	 * 
	 * @return int
	 */
	private int getSelectedSampleSize() {
		if (rdbtnBitSize8.isSelected()) {
			return 8;
		} else if (rdbtnBitSize16.isSelected()) {
			return 16;
		} else {
			return 0;
		}
	}
	
	/**
	 * Return a output path formed from output dir, filename and extension.
	 * 
	 * @return Absolute output path.
	 */
	private String getFullOutputPath() {
		return saveLocTextField.getText() 
		+ filenameTextField.getText() + "." + getSelectedExtensionRadioButtonName();
	}

	/**
	 * Get audio settings.
	 * 
	 * @return AudioSettings
	 */
	private AudioSettings getAudioSettings() {
		//get settings from the audio settings tab
		int channels = getSelectedAudioChannelCount();
		int sampleSize = getSelectedSampleSize();
		String strObject = (String)spinner.getValue();
		int sampleRate = Integer.parseInt(strObject);
		return new AudioSettings(channels, sampleSize, sampleRate);
	}
	
	/**
	 * Show codec info.
	 */
	private void showCodecInfo() {
		String filePath = fc.getFilePath();
		if (filePath != null) {
			CodecInformation dialog = new CodecInformation(frmJcaster, filePath);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);			
		} 
	}
	
	/**
	 * Display webcam on a new window.
	 */
	private void displayWebcam() {
		//show webcam on a new window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebcamDeviceAndDriver dd = new WebcamDeviceAndDriver(); 
					//get driver and device names
					String driverName = dd.getDriverName();
					String deviceName = dd.getDeviceName();
					if (driverName != null && deviceName != null) {
						new DisplayWebcamVideo(driverName, deviceName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
