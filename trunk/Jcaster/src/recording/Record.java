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
package recording;

//import java.util.Timer;
//import java.util.TimerTask;

import video.RobotVideoSource;
import audio.MicrophoneAudioSource;

import com.cattura.packet_multibroadcaster.constants.AudioVideoTypes;
import com.cattura.packet_multibroadcaster.implementations.PacketMultibroadcaster;
import com.cattura.packet_multibroadcaster.implementations.SourceGroup;

import configurations.CaptureSettings;
import constants.VideoConstants;
import encode.Container;

/**
 * Recording class is responsible for creating video and audio sources and writers which are registered to source group.
 * It is possible to record video with audio or just plain audio or video.
 * Contains implementation for setup, start and stop recording tasks.
 * 
 * @author Petri Tuononen
 *
 */
public class Record {

    /**
     * Constructor.
     */
    public Record(CaptureSettings settings) {
        RobotVideoSource robotVideoSource = null;
        MicrophoneAudioSource microphoneAudioSource = null;

        //create video and audio sources
        try {
        	//video input from the screen
            robotVideoSource = new RobotVideoSource("RobotVideoSource", VideoConstants.SCREEN_WIDTH, VideoConstants.SCREEN_HEIGHT);
            //audio input from microphone
            microphoneAudioSource = new MicrophoneAudioSource("MicrophoneAudioSource");
        }
        catch (Exception e) {
        	System.out.println("Couldn't create audio or video source(s).");
        }
        
        //create a source group
        final SourceGroup sourceGroup = PacketMultibroadcaster.makeSourceGroup(robotVideoSource, microphoneAudioSource);

        //register Writer to SourceGroup
        registerWriterToSourceGroup(sourceGroup, settings);
    }

    /**
     * Register Writer to SourceGroup
     * 
     * @param sg SourceGroup
     * @param settings CaptureSettings
     */
    public void registerWriterToSourceGroup(SourceGroup sg, CaptureSettings settings) {
    	if (settings.getAudioVideoType().equalsIgnoreCase(AudioVideoTypes.AUDIO_AND_VIDEO)) {
    		//video with audio
    		PacketMultibroadcaster.registerWriterToSourceGroup(sg,
    				new Container(settings.getOutputDirPath(), settings.getFileName(), settings.getFileType(), AudioVideoTypes.AUDIO_AND_VIDEO));
    	} else if (settings.getAudioVideoType().equalsIgnoreCase(AudioVideoTypes.AUDIO)) {
    		//only audio
    		PacketMultibroadcaster.registerWriterToSourceGroup(sg,
    				new Container(settings.getOutputDirPath(), settings.getFileName(), settings.getFileType(),  AudioVideoTypes.AUDIO));
    	} else if (settings.getAudioVideoType().equalsIgnoreCase(AudioVideoTypes.VIDEO)) {
    		//only video
    		PacketMultibroadcaster.registerWriterToSourceGroup(sg,
    				new Container(settings.getOutputDirPath(), settings.getFileName(), settings.getFileType(),  AudioVideoTypes.VIDEO));
    	}
    }

    /**
     * Start recording process.
     */
    public void startRecording() {
    	//setup processing
    	SourceGroup.setupProcessingOnAllSources();
    	//start processing
    	SourceGroup.beginProcessingOnAllSources();
    }
    
    /**
     * Stop recording process.
     */
    public void stopRecording() {
    	//stop process
    	SourceGroup.stopProcessingOnAllSources();
    }
    
}