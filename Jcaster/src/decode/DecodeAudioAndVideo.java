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
package decode;

import gui.VideoWindow;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;
import com.xuggle.xuggler.Utils;

/**
 * Takes a media container, finds the first video stream,
 * decodes that stream, and then plays the audio and video.
 *
 * This code does a VERY coarse job of matching time-stamps, and thus
 * the audio and video will float in and out of slight sync.  Getting
 * time-stamps syncing-up with audio is very system dependent and left
 * as an exercise for the reader.
 * 
 * @author aclarke
 * @author Petri Tuononen
 *
 * TODO: Sync audio and video better.
 */
public class DecodeAudioAndVideo {

	/**
	 * The audio line we'll output sound to.
	 * It'll be the default audio device on your system if available
	 */
	private static SourceDataLine mLine;

	private static VideoWindow mScreen = null;

	private static long mSystemVideoClockStartTime;

	private static long mFirstVideoTimestampInStream;

	//for testing purposes only
	public static void main(String[] args) {
		new DecodeAudioAndVideo("C:\\Users\\Pepe\\Desktop\\test.mp4");
	}
	
	/**
	 * Takes a media container (file), opens it,
	 * plays audio as quickly as it can, and opens up a Swing window and displays
	 * video frames with <i>roughly</i> the right timing.
	 *  
	 * @param filename Must contain one string which represents a filename
	 */
	@SuppressWarnings("deprecation")
	public DecodeAudioAndVideo(String filename) {
		//make sure that we can actually convert video pixel formats.
		if (!IVideoResampler.isSupported(IVideoResampler.Feature.FEATURE_COLORSPACECONVERSION))
			throw new RuntimeException("You must install the GPL version of Xuggler (with IVideoResampler support) for this demo to work");

		// ceate a Xuggler container object
		IContainer container = IContainer.make();

		//open up the container
		if (container.open(filename, IContainer.Type.READ, null) < 0)
			throw new IllegalArgumentException("Could not open file: " + filename);

		//query how many streams the call to open found
		int numStreams = container.getNumStreams();

		//iterate through the streams to find the first audio stream
		int videoStreamId = -1;
		IStreamCoder videoCoder = null;
		int audioStreamId = -1;
		IStreamCoder audioCoder = null;
		for(int i = 0; i < numStreams; i++) {
			//find the stream object
			IStream stream = container.getStream(i);
			//get the pre-configured decoder that can decode this stream;
			IStreamCoder coder = stream.getStreamCoder();

			if (videoStreamId == -1 && coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				videoStreamId = i;
				videoCoder = coder;
			}
			else if (audioStreamId == -1 && coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
				audioStreamId = i;
				audioCoder = coder;
			}
		}
		if (videoStreamId == -1 && audioStreamId == -1)
			throw new RuntimeException("Could not find audio or video stream in container: " + filename);

		/*
		 * Check if a video stream is in this file.  If so open up the decoder so it can
		 * do work.
		 */
		IVideoResampler resampler = null;
		if (videoCoder != null) {
			if(videoCoder.open() < 0)
				throw new RuntimeException("Could not open audio decoder for container: "+filename);

			if (videoCoder.getPixelType() != IPixelFormat.Type.BGR24) {
				// if this stream is not in BGR24, we're going to need to
				// convert it.  The VideoResampler does that for us.
				resampler = IVideoResampler.make(videoCoder.getWidth(), videoCoder.getHeight(), IPixelFormat.Type.BGR24,
						videoCoder.getWidth(), videoCoder.getHeight(), videoCoder.getPixelType());
				if (resampler == null)
					throw new RuntimeException("Could not create color space resampler for: " + filename);
			}
			openJavaVideo();
		}

		if (audioCoder != null) {
			if (audioCoder.open() < 0)
				throw new RuntimeException("Could not open audio decoder for container: "+filename);

			try {
				openJavaSound(audioCoder);
			}
			catch (LineUnavailableException ex) {
				throw new RuntimeException("Unable to open sound device on your system when playing back container: "+filename);
			}
		}

		//loop through the container looking at each packet.
		IPacket packet = IPacket.make();
		mFirstVideoTimestampInStream = Global.NO_PTS;
		mSystemVideoClockStartTime = 0;
		while(container.readNextPacket(packet) >= 0) {
			//check if the packet belongs to the video stream
			if (packet.getStreamIndex() == videoStreamId) {
				//allocate a new picture to get the data out of Xuggler
				IVideoPicture picture = IVideoPicture.make(videoCoder.getPixelType(),
						videoCoder.getWidth(), videoCoder.getHeight());

				//decode video and check for errors.
				int bytesDecoded = videoCoder.decodeVideo(picture, packet, 0);
				if (bytesDecoded < 0)
					throw new RuntimeException("Got error decoding audio in: " + filename);

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
							throw new RuntimeException("Could not resample video from: " + filename);
					}
					if (newPic.getPixelType() != IPixelFormat.Type.BGR24)
						throw new RuntimeException("Could not decode video as BGR 24 bit data in: " + filename);

					long delay = millisecondsUntilTimeToDisplay(newPic);
					// if there is no audio stream; go ahead and hold up the main thread.  We'll end
					// up caching fewer video pictures in memory that way.
					try {
						if (delay > 0)
							Thread.sleep(delay);
					}
					catch (InterruptedException e) {
						return;
					}

					// And finally, convert the picture to an image and display it
					mScreen.setImage(Utils.videoPictureToImage(newPic));
				}
			}
			else if (packet.getStreamIndex() == audioStreamId) {
				/*
				 * We allocate a set of samples with the same number of channels as the
				 * coder tells us is in this buffer.
				 * 
				 * We also pass in a buffer size (1024 in our example), although Xuggler
				 * will probably allocate more space than just the 1024 (it's not important why).
				 */
				IAudioSamples samples = IAudioSamples.make(1024, audioCoder.getChannels());

				/*
				 * A packet can actually contain multiple sets of samples (or frames of samples
				 * in audio-decoding speak).  So, we may need to call decode audio multiple
				 * times at different offsets in the packet's data.  We capture that here.
				 */
				int offset = 0;

				//keep going until we've processed all data
				while(offset < packet.getSize()) {
					int bytesDecoded = audioCoder.decodeAudio(samples, packet, offset);
					if (bytesDecoded < 0)
						throw new RuntimeException("Got error decoding audio in: " + filename);
					offset += bytesDecoded;
					/*
					 * Some decoder will consume data in a packet, but will not be able to construct
					 * a full set of samples yet.  Therefore you should always check if you
					 * got a complete set of samples from the decoder
					 */
					if (samples.isComplete()) {
						// note: this call will block if Java's sound buffers fill up, and we're
						// okay with that.  That's why we have the video "sleeping" occur
						// on another thread.
						playJavaSound(samples);
					}
				}
			}
			else {
				//this packet isn't part of our video stream, so we just silently drop it.
				do {} while(false);
			}

		}
		/*
		 * Technically these will be cleaned up by 
		 * the garbage collector.
		 */
		if (videoCoder != null) {
			videoCoder.close();
			videoCoder = null;
		}
		if (audioCoder != null) {
			audioCoder.close();
			audioCoder = null;
		}
		if (container != null) {
			container.close();
			container = null;
		}
		closeJavaSound();
		closeJavaVideo();
	}

	private static long millisecondsUntilTimeToDisplay(IVideoPicture picture) {
		/**
		 * We could just display the images as quickly as we decode them, but it turns
		 * out we can decode a lot faster than you think.
		 * 
		 * So instead, the following code does a poor-man's version of trying to
		 * match up the frame-rate requested for each IVideoPicture with the system
		 * clock time on your computer.
		 * 
		 * Remember that all Xuggler IAudioSamples and IVideoPicture objects always
		 * give timestamps in Microseconds, relative to the first decoded item.  If
		 * instead you used the packet timestamps, they can be in different units depending
		 * on your IContainer, and IStream and things can get hairy quickly.
		 */
		long millisecondsToSleep = 0;
		if (mFirstVideoTimestampInStream == Global.NO_PTS) {
			// This is our first time through
			mFirstVideoTimestampInStream = picture.getTimeStamp();
			// get the starting clock time so we can hold up frames
			// until the right time.
			mSystemVideoClockStartTime = System.currentTimeMillis();
			millisecondsToSleep = 0;
		} else {
			long systemClockCurrentTime = System.currentTimeMillis();
			long millisecondsClockTimeSinceStartofVideo = systemClockCurrentTime - mSystemVideoClockStartTime;
			// compute how long for this frame since the first frame in the stream.
			// remember that IVideoPicture and IAudioSamples timestamps are always in MICROSECONDS,
			// so we divide by 1000 to get milliseconds.
			long millisecondsStreamTimeSinceStartOfVideo = (picture.getTimeStamp() - mFirstVideoTimestampInStream)/1000;
			final long millisecondsTolerance = 50; // and we give ourselfs 50 ms of tolerance
			millisecondsToSleep = (millisecondsStreamTimeSinceStartOfVideo -
					(millisecondsClockTimeSinceStartofVideo+millisecondsTolerance));
		}
		return millisecondsToSleep;
	}

	/**
	 * Opens a Swing window on screen.
	 */
	private static void openJavaVideo() {
		mScreen = new VideoWindow();
	}

	/**
	 * Forces the swing thread to terminate; I'm sure there is a right
	 * way to do this in swing, but this works too.
	 */
	private static void closeJavaVideo() {
		System.exit(0);
	}

	private static void openJavaSound(IStreamCoder aAudioCoder) throws LineUnavailableException {
		
		AudioFormat audioFormat = new AudioFormat(aAudioCoder.getSampleRate(),
				(int)IAudioSamples.findSampleBitDepth(aAudioCoder.getSampleFormat()),
				aAudioCoder.getChannels(),
				true,
				false);
		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		mLine = (SourceDataLine) AudioSystem.getLine(info);
		//if that succeeded, try opening the line
		mLine.open(audioFormat);
		//and if that succeed, start the line
		mLine.start();
	}

	private static void playJavaSound(IAudioSamples aSamples) {
		//dump all the samples into the line.
		byte[] rawBytes = aSamples.getData().getByteArray(0, aSamples.getSize());
		mLine.write(rawBytes, 0, aSamples.getSize());
	}
     
	private static void closeJavaSound() {
		if (mLine != null) {
			//wait for the line to finish playing
			mLine.drain();
			//close the line
			mLine.close();
			mLine = null;
		}
	}
	
}
