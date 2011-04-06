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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JcasterGUI {

	private JFrame frmJcaster;
	private static Record record;

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
		setupRecording();
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
		
		final JButton btnRecord = new JButton("Record");
		final JButton btnStop = new JButton("Stop");
		final JButton btnPause = new JButton("Pause");
		
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
		
		btnRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnRecord.setEnabled(false);
				btnStop.setEnabled(true);
				btnPause.setEnabled(true);
				//start recording
				record.startRecording();
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
				btnStop.setEnabled(false);
				btnPause.setEnabled(false);
				btnRecord.setEnabled(true);
				btnRecord.setEnabled(true);
				//stop recording
				record.stopRecording();
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		frmJcaster.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("General settings", null, panel_2, null);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblSaveLocation = new JLabel("Save location");
		GridBagConstraints gbc_lblSaveLocation = new GridBagConstraints();
		gbc_lblSaveLocation.anchor = GridBagConstraints.WEST;
		gbc_lblSaveLocation.gridx = 0;
		gbc_lblSaveLocation.gridy = 1;
		panel_2.add(lblSaveLocation, gbc_lblSaveLocation);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Video settings", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblFormat = new JLabel("Format");
		GridBagConstraints gbc_lblFormat = new GridBagConstraints();
		gbc_lblFormat.gridx = 0;
		gbc_lblFormat.gridy = 1;
		panel.add(lblFormat, gbc_lblFormat);
		
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
		
		JMenuItem mntmRecord = new JMenuItem("Record");
		mnNewMenu.add(mntmRecord);
		
		JMenuItem mntmStop = new JMenuItem("Stop");
		mnNewMenu.add(mntmStop);
		
		JMenuItem mntmPause = new JMenuItem("Pause");
		mnNewMenu.add(mntmPause);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnNewMenu.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
	}
	
    /**
     * Setup recording process.
     */
    private void setupRecording() {
    	CaptureSettings settings = new CaptureSettings(); //default settings
    	record = new Record(settings);
    }
    
}
