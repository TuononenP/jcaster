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
package transcode;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.xuggle.mediatool.IMediaReader;
//import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

/**
 * Transcode a media file from one format to another.
 * 
 * @author Petri Tuononen
 *
 * TODO: Add a check that if input and output formats are same
 * 		 then there is not need to transcode.
 */
public class Transcode {

	/**
	 * Transcode file from one format to another.
	 * 
	 * @param input Absolute input path in string format.
	 * @param output Absolute input path in string format.
	 * @param mainFrame
	 */
	public Transcode(String inputPath, String outputPath, JFrame mainFrame) {
		//create a media reader
		IMediaReader mediaReader = ToolFactory.makeReader(inputPath);

		//create a media writer
		IMediaWriter mediaWriter = ToolFactory.makeWriter(outputPath, mediaReader);

		//add a writer to the reader, to create the output file
		mediaReader.addListener(mediaWriter);

		//create a media viewer with stats enabled
//		IMediaViewer mediaViewer = ToolFactory.makeViewer(true);

		//add a viewer to the reader, to see the decoded media
//		mediaReader.addListener(mediaViewer);

		//read and decode packets from the source file and
		//dispatch decoded audio and video to the writer
		while (mediaReader.readPacket() == null);
		JOptionPane.showMessageDialog(mainFrame, "Transcoding finished.");
	}
	
}
