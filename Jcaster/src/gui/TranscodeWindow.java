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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import transcode.Transcode;

/**
 * Transcodes a file from one format to another.
 * 
 * @author Petri
 *
 */
public class TranscodeWindow extends JFrame {

	private static final long serialVersionUID = 3767734593941102512L;
	private JPanel contentPane;
	private JTextField txtInputPath;
	private JTextField outputFilename;
	private JTextField outputFilePath;
	private JSpinner spinner;
	private FileChooser chooser;
	private JFrame mainFrame;

	/**
	 * Create the frame.
	 */
	public TranscodeWindow(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		setTitle("Transcode");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 245);
		setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{106, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{20, 0, 0, 0, 0, 30, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblInputFilePath = new JLabel("Input file path");
		GridBagConstraints gbc_lblInputFilePath = new GridBagConstraints();
		gbc_lblInputFilePath.anchor = GridBagConstraints.WEST;
		gbc_lblInputFilePath.insets = new Insets(0, 0, 5, 5);
		gbc_lblInputFilePath.gridx = 0;
		gbc_lblInputFilePath.gridy = 1;
		contentPane.add(lblInputFilePath, gbc_lblInputFilePath);
		
		txtInputPath = new JTextField();
		GridBagConstraints gbc_txtInputPath = new GridBagConstraints();
		gbc_txtInputPath.insets = new Insets(0, 0, 5, 5);
		gbc_txtInputPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtInputPath.gridx = 1;
		gbc_txtInputPath.gridy = 1;
		contentPane.add(txtInputPath, gbc_txtInputPath);
		txtInputPath.setColumns(10);
		
		JButton btnBrowseInput = new JButton("Browse...");
		GridBagConstraints gbc_btnBrowseInput = new GridBagConstraints();
		gbc_btnBrowseInput.anchor = GridBagConstraints.WEST;
		gbc_btnBrowseInput.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrowseInput.gridx = 2;
		gbc_btnBrowseInput.gridy = 1;
		btnBrowseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInputFilePath();
			}
		});
		contentPane.add(btnBrowseInput, gbc_btnBrowseInput);
		
		JLabel lblOutputFilename = new JLabel("Output filename");
		GridBagConstraints gbc_lblOutputFilename = new GridBagConstraints();
		gbc_lblOutputFilename.anchor = GridBagConstraints.WEST;
		gbc_lblOutputFilename.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutputFilename.gridx = 0;
		gbc_lblOutputFilename.gridy = 2;
		contentPane.add(lblOutputFilename, gbc_lblOutputFilename);
		
		outputFilename = new JTextField();
		GridBagConstraints gbc_outputFilename = new GridBagConstraints();
		gbc_outputFilename.insets = new Insets(0, 0, 5, 5);
		gbc_outputFilename.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputFilename.gridx = 1;
		gbc_outputFilename.gridy = 2;
		contentPane.add(outputFilename, gbc_outputFilename);
		outputFilename.setColumns(10);
		
		JLabel lblOutputFormat = new JLabel("Output format");
		GridBagConstraints gbc_lblOutputFormat = new GridBagConstraints();
		gbc_lblOutputFormat.anchor = GridBagConstraints.WEST;
		gbc_lblOutputFormat.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutputFormat.gridx = 0;
		gbc_lblOutputFormat.gridy = 3;
		contentPane.add(lblOutputFormat, gbc_lblOutputFormat);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerListModel(new String[] {"mp4", "mov"}));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 3;
		contentPane.add(spinner, gbc_spinner);
		
		JLabel lblOutputDir = new JLabel("Output dir");
		GridBagConstraints gbc_lblOutputFir = new GridBagConstraints();
		gbc_lblOutputFir.anchor = GridBagConstraints.WEST;
		gbc_lblOutputFir.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutputFir.gridx = 0;
		gbc_lblOutputFir.gridy = 4;
		contentPane.add(lblOutputDir, gbc_lblOutputFir);
		
		outputFilePath = new JTextField();
		GridBagConstraints gbc_outputFilePath = new GridBagConstraints();
		gbc_outputFilePath.insets = new Insets(0, 0, 5, 5);
		gbc_outputFilePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputFilePath.gridx = 1;
		gbc_outputFilePath.gridy = 4;
		contentPane.add(outputFilePath, gbc_outputFilePath);
		outputFilePath.setColumns(10);
		
		JButton btnBrowseOutput = new JButton("Browse...");
		GridBagConstraints gbc_btnBrowseOutput = new GridBagConstraints();
		gbc_btnBrowseOutput.anchor = GridBagConstraints.WEST;
		gbc_btnBrowseOutput.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrowseOutput.gridx = 2;
		gbc_btnBrowseOutput.gridy = 4;
		btnBrowseOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOutputDir();
			}
		});
		contentPane.add(btnBrowseOutput, gbc_btnBrowseOutput);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		contentPane.add(panel, gbc_panel);
		
		JButton btnTranscode = new JButton("Transcode");
		btnTranscode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transcode();
			}
		});
		panel.add(btnTranscode);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCancel);
		
		chooser = new FileChooser(mainFrame);
	}

	/**
	 * Transcode file from one format to another.
	 * 
	 * @param outputFilename Absolute path in string format.
	 */
	private void transcode() {
		//get input and output paths
		String inputPath, outputPath;
		inputPath = txtInputPath.getText();
		outputPath = outputFilePath.getText() + File.separator + outputFilename.getText() + "." + getOutputformat();
		
		new Transcode(inputPath, outputPath, mainFrame);
	}
	
	/**
	 * Get the output format from the spinner.
	 * 
	 * @return String Output format.
	 */
	private String getOutputformat() {
		return (String)spinner.getValue();
	}
	
	/**
	 * set the input file path.
	 */
	private void setInputFilePath() {
		String path = chooser.getFilePath();
		if (path != null) {
			txtInputPath.setText(path);
		}
	}
	
	/**
	 * Set the output dir.
	 */
	private void setOutputDir() {
		String dir = chooser.getDirectoryPath();
		if (dir != null) {
			outputFilePath.setText(dir);
		}
	}

}
