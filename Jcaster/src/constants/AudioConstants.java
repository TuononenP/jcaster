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
package constants;


/**
 * Constants for Audio.
 * 
 * @author Petri Tuononen
 *
 */
public class AudioConstants {
	
    // Constants
    public static final int TARGET_DATA_LINE_BYTE_SIZE = 50000;                 //  Size of the buffer that is transfered between the Microphone input and the Xuggler IAudioSamples
	public static final float SAMPLE_RATE = 44100.0F;                           //  8000, 11025, 16000, 22050, 44100 in Hz
	public static final int SAMPLE_RATE_AS_INT = (int)SAMPLE_RATE;              				//  8000, 11025, 16000, 22050, 44100 in Hz
	public static final int SAMPLE_SIZE_IN_BITS = 16;                           //  8, 16
	public static final int NUMBER_OF_CHANNELS = 2;                             //  1, 2
	public static final boolean SIGNED = true;                                  //  true, false
	public static final boolean BIG_ENDIAN = false;                             //  true, false

}