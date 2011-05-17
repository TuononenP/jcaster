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

/**
 * Get OS specific device and driver names.
 * 
 * @author Petri Tuononen
 *
 */
public class WebcamDeviceAndDriver {

	//global variables
	private String driverName;
	private String deviceName;

	public String getDriverName() {
		return driverName;
	}

	private void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	private void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * Initialize.
	 */
	public WebcamDeviceAndDriver() {
		String os = getOS();
		//choose correct driver and device name specific to the OS
		if ( (os.indexOf("win") >= 0) == true ) { //Windows
			setDriverName("vfwcap");
			setDeviceName("0");
		} else if ( (os.indexOf("nix") >=0 || os.indexOf("nux") >=0) == true ) { //Linux
			setDriverName("video4linux2");
			setDeviceName("/dev/video0");
		} else if ( (os.indexOf("mac") >= 0) == true ) { //Mac
			//TODO: find out driver and device names for mac. Note: FFmpeg may not even have Mac webcam support.
		}
	}

	/**
	 * Detect operating system.
	 * 
	 * @return Operating systems acronym as a String
	 */
	private String getOS() {
		return System.getProperty("os.name").toLowerCase();
	}

}
