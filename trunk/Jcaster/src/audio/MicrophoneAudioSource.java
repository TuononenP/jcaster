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
package audio;

//  Native Java Classes
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.cattura.packet_multibroadcaster.constants.AudioVideoTypes;
import com.cattura.packet_multibroadcaster.implementations.Source;
import com.cattura.packet_multibroadcaster.value_objects.AudioPacket;

import configurations.AudioSettings;
import constants.AudioConstants;

public class MicrophoneAudioSource extends Source {
	
	//  Constants
	private final byte[] TEMP_BUFFER;

	//  Instance variables
	private TargetDataLine _targetDataLine;

	//----------------------------------------------------------------------------------------------
	//  Initialize
	//----------------------------------------------------------------------------------------------
	public MicrophoneAudioSource (String $id, AudioSettings audioSettings) {
		super($id, AudioVideoTypes.AUDIO);

		TEMP_BUFFER = new byte[AudioConstants.TARGET_DATA_LINE_BYTE_SIZE];

		try {
			_targetDataLine = (TargetDataLine)AudioSystem.getLine(new DataLine.Info(TargetDataLine.class,
					audioSettings.configuredAudioFormat));
			_targetDataLine.open(audioSettings.getConfiguredAudioFormat());
			_targetDataLine.start();
		}
		catch (LineUnavailableException $ignore) {
		}
	}

	//----------------------------------------------------------------------------------------------
	//  Functions
	//----------------------------------------------------------------------------------------------
	@Override
	protected void packAudioPacket (AudioPacket $audioPacket) {
		if (_targetDataLine.read(TEMP_BUFFER, 0, TEMP_BUFFER.length) > 0) {
			$audioPacket.pack(System.nanoTime(), TEMP_BUFFER);
		}
	}

}