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

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * 
 * Shared methods that return file or directory
 * that is selected using JFileChooser.
 * 
 * @author Petri Tuononen
 *
 */
public class FileChooser {

	private JFrame mainFrame;

	/**
	 * Constructor.
	 * 
	 * @param mainFrame
	 */
	public FileChooser(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * Get the selected absolute path of the file.
	 * 
	 * @return String absolute file path
	 */
	protected String getFilePath() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose file");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnVal = chooser.showOpenDialog(mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			return file.getAbsolutePath();
		} else {
			return null;
		}
	}

	/**
	 * Get absolute path of the directory.
	 * 
	 * @return String Absolute directory path. 
	 * 
	 */
	protected String getDirectoryPath() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		int returnVal = chooser.showOpenDialog(mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			return file.getAbsolutePath();
		} else {
			return null;
		}
	}

}
