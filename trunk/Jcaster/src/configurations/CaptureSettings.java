package configurations;

//Copyright (C) 2011  Petri Tuononen
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
import java.io.File;

import com.cattura.packet_multibroadcaster.constants.AudioVideoTypes;

public class CaptureSettings {

    // Variables
    private String audioVideoType;
    private String fileName;
    private String fileType;
    private String outputDirPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    private int captureDuration = 10000;
    
    // Getters and Setters
    
    /**
     * Default constructor.
     * Captures video with audio.
     */
	public CaptureSettings() {
    	setAudioVideoType(AudioVideoTypes.AUDIO_AND_VIDEO);
    	setFileName("test");
    	setFileType("mp4");
    	setOutputDirPath(System.getProperty("user.home") + File.separator + "Desktop" + File.separator); //outputs to desktop
    	setCaptureDuration(10000); //records 10s TODO: REMOVE/CHANGE
	}

	/**
	 * Specific capture settings.
	 * 
	 * @param audioVideoType
	 * @param fileName
	 * @param fileType
	 * @param outputDirPath
	 * @param captureDuration TODO: REMOVE/CHANGE
	 */
	public CaptureSettings(String audioVideoType, String fileName, String fileType, String outputDirPath, int captureDuration) {
    	setAudioVideoType(audioVideoType);
    	setFileName(fileName);
    	setFileType(fileType);
    	setOutputDirPath(outputDirPath);
    	setCaptureDuration(captureDuration);
	}

	public String getAudioVideoType() {
		return audioVideoType;
	}

	public int getCaptureDuration() {
		return captureDuration;
	}
	
	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

    public String getOutputDirPath() {
		return outputDirPath;
	}

	public void setAudioVideoType(String audioVideoType) {
		this.audioVideoType = audioVideoType;
	}

	public void setCaptureDuration(int captureDuration) {
		this.captureDuration = captureDuration;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public void setOutputDirPath(String outputDirPath) {
		this.outputDirPath = outputDirPath;
	}
	
}
