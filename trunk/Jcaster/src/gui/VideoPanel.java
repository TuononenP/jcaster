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

import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import constants.VideoConstants;

/**
 * Class used to display video and webcam playback.
 * 
 * @author Petri Tuononen
 */
public class VideoPanel extends JPanel {

	private static final long serialVersionUID = 4548127773665440463L;
	private final ImageComponent imageComp;

	/**
	 * Create the frame
	 */
	public VideoPanel() {
		imageComp = new ImageComponent();
		setSize(VideoConstants.SCREEN_WIDTH, VideoConstants.SCREEN_HEIGHT);
		add(imageComp);
		setVisible(true);
	}

	protected void setImage(BufferedImage aImage) {
		imageComp.setImage(aImage);
	}

//	public class ImageComponent extends JComponent {
//
//		private static final long serialVersionUID = 5285842446449759187L;
//		private Image mImage;
//		private Dimension mSize;
//
//		private void setImage(Image image) {
//			SwingUtilities.invokeLater(new ImageRunnable(image));
//		}
//
//		private class ImageRunnable implements Runnable {
//			private final Image newImage;
//
//			public ImageRunnable(Image newImage) {
//				this.newImage = newImage;
//			}
//
//			public void run() {
//				ImageComponent.this.mImage = newImage;
//				final Dimension newSize = new Dimension(mImage.getWidth(null), mImage.getHeight(null));
//				if (!newSize.equals(mSize)) {
//					ImageComponent.this.mSize = newSize;
////					VideoPanel.this.setSize(mImage.getWidth(null), mImage.getHeight(null));
////					VideoPanel.this.setVisible(true);
//				}
//				repaint();
//			}
//		}
//
//		public ImageComponent() {
//			mSize = new Dimension(VideoConstants.SCREEN_WIDTH, VideoConstants.SCREEN_HEIGHT); //TODO change fixed frame size
//			setSize(mSize);
//		}
//
//		public synchronized void paint(Graphics g) {
//			if (mImage != null)
//				g.drawImage(mImage, 0, 0, this);
//		}
//
//	}
	
}
