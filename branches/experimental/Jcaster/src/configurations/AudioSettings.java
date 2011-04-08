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
package configurations;

import javax.sound.sampled.AudioFormat;

import constants.AudioConstants;

/**
 * Settings for audio.
 * 
 * @author Petri Tuononen
 *
 */
public class AudioSettings {

//	private float sampleRate = 44100.0F;                        //  8000, 11025, 16000, 22050, 44100 in Hz
//	private int sampleRateAsInt = (int)sampleRate;              //  8000, 11025, 16000, 22050, 44100 in Hz
	private int sampleRateAsInt = 44100;                        //  8000, 11025, 16000, 22050, 44100 in Hz
	private int sampleSizeInBits = 16;                          //  8, 16
	private int numberOfChannels = 2;                           //  1, 2

	public AudioFormat configuredAudioFormat = new AudioFormat(AudioConstants.SAMPLE_RATE,
			getSampleSizeInBits(), getNumberOfChannels(), AudioConstants.SIGNED,
    		AudioConstants.BIG_ENDIAN);
	
	/**
	 * Constructor.
	 * 
	 * @param channels
	 * @param sampleSize
	 * @param sampleRate
	 */
	public AudioSettings(int channels, int sampleSize, int sampleRate) {
		setNumberOfChannels(channels);
		setSampleSizeInBits(sampleSize);
		setSampleRateAsInt(sampleRate);
		
	}
	
//	public float getSampleRate() {
//		return sampleRate;
//	}
//
//	public void setSampleRate(float sampleRate) {
//		this.sampleRate = sampleRate;
//	}

	public int getSampleRateAsInt() {
		return sampleRateAsInt;
	}

	public void setSampleRateAsInt(int sampleRateAsInt) {
		this.sampleRateAsInt = sampleRateAsInt;
	}

	public int getSampleSizeInBits() {
		return sampleSizeInBits;
	}

	public void setSampleSizeInBits(int sampleSizeInBits) {
		this.sampleSizeInBits = sampleSizeInBits;
	}

	public int getNumberOfChannels() {
		return numberOfChannels;
	}

	public void setNumberOfChannels(int numberOfChannels) {
		this.numberOfChannels = numberOfChannels;
	}

	public AudioFormat getConfiguredAudioFormat() {
		return configuredAudioFormat;
	}

}
