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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

import javax.swing.JTextPane;

/**
 * Dialog that contains audio/video file information.
 * 
 * @author Petri Tuononen
 *
 */
public class CodecInformation extends JDialog {

	private static final long serialVersionUID = -5882743133690066249L;
	private final JPanel contentPanel = new JPanel();
	private JFrame mainFrame;
	
	/**
	 * Create the dialog.
	 */
	public CodecInformation(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		setTitle("Codec information");
		setBounds(100, 100, 679, 271);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JTextPane txtCodecInfo = new JTextPane();
				txtCodecInfo.setOpaque(false);
				txtCodecInfo.setEditable(false);
				txtCodecInfo.setText(getFormattedCodecInformation());
				panel.add(txtCodecInfo);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
	}

	private String getFormattedCodecInformation() {
		//browse for a file and get it's path
		String filePath = getFilePath();

		//create a Xuggler container
		IContainer container = IContainer.make();

		//attempt to open up the container
		int result = container.open(filePath, IContainer.Type.READ, null);

		//check if the operation was successful
		if (result < 0)
			throw new RuntimeException("Failed to open media file");

		//get number of streams the container contains
		int numStreams = container.getNumStreams();

		//get total duration
		long duration = container.getDuration();

		//get file size in bytes
		double fileSize = container.getFileSize();
		//convert file size to MBs
		fileSize = fileSize/1048576;
		//round to two decimals
		fileSize = (double) Math.round(fileSize * 100) / 100;

		//get bit rate
		long bitRate = container.getBitRate();

		StringBuilder sB = new StringBuilder();

		sB.append("Number of streams: " + numStreams + "\n");
		sB.append("Duration (s): " + duration/1000000 + "\n");
		sB.append("File Size (MB): " + fileSize + "\n");
		sB.append("Bit Rate: " + bitRate + "\n");
		sB.append("\n");
		
		// iterate through the streams to access their meta data
		for (int i=0; i<numStreams; i++) {

			// find the stream object
			IStream stream = container.getStream(i);

			// get the pre-configured decoder that can decode this stream;
			IStreamCoder coder = stream.getStreamCoder();

			sB.append("stream " + i + " ");
			sB.append("type: " + coder.getCodecType() + "; ");
			sB.append("duration: " + stream.getDuration() + "; ");
			sB.append("start time: " + container.getStartTime() + "; ");
			sB.append("timebase: " + stream.getTimeBase().getNumerator() + ", " + stream.getTimeBase().getDenominator() + "; ");
			sB.append("coder tb: " + coder.getTimeBase().getNumerator() + ", " + coder.getTimeBase().getDenominator() + "; ");
			sB.append("\n\t");

			if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
				sB.append("sample rate: " + coder.getSampleRate() + "; ");
				sB.append("channels: " + coder.getChannels() + "; ");
				sB.append("format: " + coder.getSampleFormat() + "; ");
			} else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				sB.append("width: " + coder.getWidth() + "; ");
				sB.append("height: " + coder.getHeight() + "; ");
				sB.append("format: " + coder.getPixelType() + "; ");
				sB.append("frame-rate: " + coder.getFrameRate().getDouble() + "; ");
			}

			sB.append("\n");
		}
		return sB.toString();
	}

	/**
	 * Get the selected absolute path of the file.
	 * 
	 * @return String absolute file path
	 */
	private String getFilePath() {
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

}
