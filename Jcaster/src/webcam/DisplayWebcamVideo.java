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
package webcam;

import java.awt.image.BufferedImage;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
//import com.xuggle.xuggler.IContainerParameters;
import com.xuggle.xuggler.IError;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;
import com.xuggle.xuggler.Utils;
import com.xuggle.xuggler.demos.VideoImage;

/**
 * Takes a FFMPEG device driver name (ex: "video4linux2"), and a device name (ex: /dev/video0), and displays video
 * from that device.  For example, a web camera.
 * 
 * @author aclarke
 * @author Petri Tuononen
 *
 */
public class DisplayWebcamVideo {
	/**
	 * Takes a FFMPEG webcam driver name, and a device name, opens the
	 * webcam, and displays its video in a Swing window.
	 * 
	 * <p>
	 * Webcam support is very limited; you can't query what devices are
	 * available, nor can you query what their capabilities are without
	 * actually opening the device.  Sorry, but that's how FFMPEG rolls.
	 * </p>
	 * 
	 * @param driverName FFMPEG driver name
	 * @param deviceName Device name dependent on the FFMPEG driver
	 */
	@SuppressWarnings("deprecation")
	public DisplayWebcamVideo(String driverName, String deviceName) {
		// Let's make sure that we can actually convert video pixel formats.
		if (!IVideoResampler.isSupported(IVideoResampler.Feature.FEATURE_COLORSPACECONVERSION))
			throw new RuntimeException("you must install the GPL version of Xuggler (with IVideoResampler support)");

		// Create a Xuggler container object
		IContainer container = IContainer.make();

		// Devices, unlike most files, need to have parameters set in order
		// for Xuggler to know how to configure them.  For a webcam, these
		// parameters make sense
		//IContainerParameters params = IContainerParameters.make();

		// The timebase here is used as the camera frame rate
		//params.setTimeBase(IRational.make(30,1)); //30fps

		// we need to tell the driver what video with and height to use
		//params.setVideoWidth(320);
		//params.setVideoHeight(240);

		// and finally, we set these parameters on the container before opening
		//container.setParameters(params);

		// Tell Xuggler about the device format
		IContainerFormat format = IContainerFormat.make();
		if (format.setInputFormat(driverName) < 0)
			throw new IllegalArgumentException("couldn't open webcam device: " + driverName);

		// Open up the container
		int retval = container.open(deviceName, IContainer.Type.READ, format);
		if (retval < 0) {
			// This little trick converts the non friendly integer return value into
			// a slightly more friendly object to get a human-readable error name
			IError error = IError.make(retval);
			throw new IllegalArgumentException("could not open file: " + deviceName + "; Error: " + error.getDescription());
		}      

		// query how many streams the call to open found
		int numStreams = container.getNumStreams();

		// iterate through the streams to find the first video stream
		int videoStreamId = -1;
		IStreamCoder videoCoder = null;
		for(int i = 0; i < numStreams; i++) {
			// Find the stream object
			IStream stream = container.getStream(i);
			// Get the pre-configured decoder that can decode this stream;
			IStreamCoder coder = stream.getStreamCoder();

			if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				videoStreamId = i;
				videoCoder = coder;
				break;
			}
		}
		if (videoStreamId == -1)
			throw new RuntimeException("could not find video stream in container: "+deviceName);

		/*
		 * Now we have found the video stream in this file.  Let's open up our decoder so it can
		 * do work.
		 */
		if (videoCoder.open() < 0)
			throw new RuntimeException("could not open video decoder for container: "+deviceName);

		IVideoResampler resampler = null;
		if (videoCoder.getPixelType() != IPixelFormat.Type.BGR24) {
			// if this stream is not in BGR24, we're going to need to
			// convert it.  The VideoResampler does that for us.
			resampler = IVideoResampler.make(videoCoder.getWidth(), videoCoder.getHeight(), IPixelFormat.Type.BGR24,
					videoCoder.getWidth(), videoCoder.getHeight(), videoCoder.getPixelType());
			if (resampler == null)
				throw new RuntimeException("could not create color space resampler for: " + deviceName);
		}
		/*
		 * And once we have that, we draw a window on screen
		 */
		openJavaWindow();

		/*
		 * Now, we start walking through the container looking at each packet.
		 */
		IPacket packet = IPacket.make();
		while(container.readNextPacket(packet) >= 0) {
			/*
			 * Now we have a packet, let's see if it belongs to our video stream
			 */
			if (packet.getStreamIndex() == videoStreamId) {
				/*
				 * We allocate a new picture to get the data out of Xuggler
				 */
				IVideoPicture picture = IVideoPicture.make(videoCoder.getPixelType(),
						videoCoder.getWidth(), videoCoder.getHeight());

				int offset = 0;
				while(offset < packet.getSize()) {
					/*
					 * Now, we decode the video, checking for any errors.
					 * 
					 */
					int bytesDecoded = videoCoder.decodeVideo(picture, packet, offset);
					if (bytesDecoded < 0)
						throw new RuntimeException("got error decoding video in: " + deviceName);
					offset += bytesDecoded;

					/*
					 * Some decoders will consume data in a packet, but will not be able to construct
					 * a full video picture yet.  Therefore you should always check if you
					 * got a complete picture from the decoder
					 */
					if (picture.isComplete()) {
						IVideoPicture newPic = picture;
						/*
						 * If the resampler is not null, that means we didn't get the video in BGR24 format and
						 * need to convert it into BGR24 format.
						 */
						if (resampler != null) {
							// we must resample
							newPic = IVideoPicture.make(resampler.getOutputPixelFormat(), picture.getWidth(), picture.getHeight());
							if (resampler.resample(newPic, picture) < 0)
								throw new RuntimeException("could not resample video from: " + deviceName);
						}
						if (newPic.getPixelType() != IPixelFormat.Type.BGR24)
							throw new RuntimeException("could not decode video as BGR 24 bit data in: " + deviceName);

						// Convert the BGR24 to an Java buffered image
						BufferedImage javaImage = Utils.videoPictureToImage(newPic);

						// and display it on the Java Swing window
						updateJavaWindow(javaImage);
					}
				}
			}
			else
			{
				/*
				 * This packet isn't part of our video stream, so we just silently drop it.
				 */
				do {} while(false);
			}

		}
		/*
		 * Technically since we're exiting anyway, these will be cleaned up by 
		 * the garbage collector... but because we're nice people and want
		 * to be invited places for Christmas, we're going to show how to clean up.
		 */
		if (videoCoder != null) {
			videoCoder.close();
			videoCoder = null;
		}
		if (container !=null) {
			container.close();
			container = null;  

		}
		closeJavaWindow();

	}

	/**
	 * The window we'll draw the video on.
	 * 
	 */
	private static VideoImage mScreen = null;

	private static void updateJavaWindow(BufferedImage javaImage) {
		mScreen.setImage(javaImage);
	}

	/**
	 * Opens a Swing window on screen.
	 */
	private static void openJavaWindow() {
		mScreen = new VideoImage();
	}

	/**
	 * Forces the swing thread to terminate; I'm sure there is a right
	 * way to do this in swing, but this works too.
	 */
	private static void closeJavaWindow() {
		System.exit(0);
	}

}
