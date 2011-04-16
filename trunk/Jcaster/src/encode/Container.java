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
package encode;

//  Native Java Classes
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import com.cattura.packet_multibroadcaster.implementations.Writer;
import com.cattura.packet_multibroadcaster.value_objects.AudioPacket;
import com.cattura.packet_multibroadcaster.value_objects.VideoPacket;
import com.xuggle.ferry.IBuffer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.video.ConverterFactory;

import configurations.AudioSettings;
import constants.EncodingConstants;
import constants.VideoConstants;

public class Container extends Writer {
	
    private static final TimeUnit NANOSECONDS_TIME_UNIT = TimeUnit.NANOSECONDS;
    private IMediaWriter _mediaWriter;
    private AudioSettings audioSettings;
    
    public Container(String $ouputDirectory, String $filename, String $extension, String $type, AudioSettings audioSettings) {
        super($ouputDirectory, $filename, $extension, $type);
        this.audioSettings = audioSettings;
    }

    @Override
	public void encodeAudio(AudioPacket $packet) {
        byte[] audioSamplesByteArray = $packet.getAudioSamplesAsByteArray();
        int audioSamplesByteArrayLength = audioSamplesByteArray.length;

        if (audioSamplesByteArrayLength > 0) {
            IBuffer audioBuffer = IBuffer.make(null, audioSamplesByteArray, 0, audioSamplesByteArrayLength);
            audioBuffer.setType(IBuffer.Type.IBUFFER_SINT16);

            IAudioSamples audioSamples = IAudioSamples.make(audioBuffer, 1, IAudioSamples.Format.FMT_S16);
            audioSamples.setComplete(true, audioBuffer.getSize(), audioSettings.getSampleRateAsInt(), audioSettings.getNumberOfChannels(), IAudioSamples.Format.FMT_S16, Global.NO_PTS);

            _mediaWriter.encodeAudio(EncodingConstants.AUDIO_STREAM_ID, audioSamples);
        }
    }

    @Override
	public void encodeVideo(VideoPacket $packet) {
        _mediaWriter.encodeVideo(EncodingConstants.VIDEO_STREAM_ID, ConverterFactory.convertToType($packet.getBufferedImage(), BufferedImage.TYPE_3BYTE_BGR), $packet.getTimestamp(), NANOSECONDS_TIME_UNIT);
    }

    @Override
	public void finalizeProcessing() {
        super.finalizeProcessing();

        _mediaWriter.flush();
        _mediaWriter.close();
    }

    @Override
	public void setupProcessing() {
        super.setupProcessing();

        _mediaWriter = ToolFactory.makeWriter(getOutputDirectory() + File.separator + getFilename() + "." + getExtension());
        _mediaWriter.open();
        _mediaWriter.setForceInterleave(true);

        if (getSupportsVideo()) {
            _mediaWriter.addVideoStream(EncodingConstants.VIDEO_STREAM_ID, 0, ICodec.guessEncodingCodec(null, null, getOutputDirectory() + File.separator + getFilename() + "." + getExtension(), null, ICodec.Type.CODEC_TYPE_VIDEO), VideoConstants.SCREEN_WIDTH, VideoConstants.SCREEN_HEIGHT);
        }

        if (getSupportsAudio()) {
            _mediaWriter.addAudioStream(EncodingConstants.AUDIO_STREAM_ID, 0, ICodec.guessEncodingCodec(null, null, getOutputDirectory() + File.separator + getFilename() + "." + getExtension(), null, ICodec.Type.CODEC_TYPE_AUDIO), audioSettings.getNumberOfChannels(), audioSettings.getSampleRateAsInt());
        }
    }

}