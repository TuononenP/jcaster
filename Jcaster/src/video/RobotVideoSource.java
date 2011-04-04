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
package video;

//  Native Java Classes
import java.awt.Rectangle;
import java.awt.Robot;

import com.cattura.packet_multibroadcaster.constants.AudioVideoTypes;
import com.cattura.packet_multibroadcaster.implementations.Source;
import com.cattura.packet_multibroadcaster.value_objects.VideoPacket;

public class RobotVideoSource extends Source
{
    //  Constants
    private final Rectangle SCREEN_RECTANGLE;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    
    //  Instance variables
    private Robot _robot;

    //----------------------------------------------------------------------------------------------
    //  Initialize
    //----------------------------------------------------------------------------------------------
    public RobotVideoSource (String $id, int $screenWidth, int $screenHeight) throws Exception
    {
        super($id, AudioVideoTypes.VIDEO);

        SCREEN_WIDTH = $screenWidth;
        SCREEN_HEIGHT = $screenHeight;
        SCREEN_RECTANGLE = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        _robot = new Robot();
    }

    //----------------------------------------------------------------------------------------------
    //  Functions
    //----------------------------------------------------------------------------------------------
    @Override
	protected void packVideoPacket (VideoPacket $videoPacket)
    {
        $videoPacket.pack(System.nanoTime(), _robot.createScreenCapture(SCREEN_RECTANGLE));
    }

}