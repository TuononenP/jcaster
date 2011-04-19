package gui;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class VideoFrame extends JFrame{

	private static final long serialVersionUID = 2701479414077997717L;
	private VideoPanel videoPanel;
	
	public VideoFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		videoPanel = new VideoPanel();
		getContentPane().add(videoPanel);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	public void setImage(final BufferedImage aImage) {
		videoPanel.setImage(aImage);
	}
	
}
