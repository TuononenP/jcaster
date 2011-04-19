package gui;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import constants.VideoConstants;

public class ImageComponent extends JComponent {

	private static final long serialVersionUID = 6719276619890160914L;
	private Image mImage;
	private Dimension mSize;

	public void setImage(Image image) {
		SwingUtilities.invokeLater(new ImageRunnable(image));
	}

	private class ImageRunnable implements Runnable {
		private final Image newImage;

		public ImageRunnable(Image newImage) {
			super();
			this.newImage = newImage;
		}

		public void run() {
			ImageComponent.this.mImage = newImage;
			final Dimension newSize = new Dimension(mImage.getWidth(null), mImage.getHeight(null));
			if (!newSize.equals(mSize)) {
				ImageComponent.this.mSize = newSize;
				setSize(mImage.getWidth(null), mImage.getHeight(null));
				setVisible(true);
			}
			repaint();
		}
	}

	public ImageComponent() {
		mSize = new Dimension(VideoConstants.SCREEN_WIDTH, VideoConstants.SCREEN_HEIGHT); //TODO change fixed frame size
		setSize(mSize);
	}

	public synchronized void paint(Graphics g) {
		if (mImage != null)
			g.drawImage(mImage, 0, 0, this);
	}

}