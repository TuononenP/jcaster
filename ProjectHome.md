<p>Powerful, easy-to-use screencasting software which records video and audio synchronously or asynchronously.</p>
<img src='http://jcaster.googlecode.com/files/jcaster.png' alt='Screenshot' />

<b>Development on hiatus.</b>

## Features ##
  * High quality full screen audio&video capture/screencasting
  * Uses very powerful FFmpeg audio/video codec library
  * User-friendly GUI
  * cross-platfrom (Linux, Windows, Mac OS X)
  * Supported video codecs: mp4, mov
  * Supported audio codecs: mp3, ogg vorbis, aac, wav
  * Audio/video stream information
  * Transcode from one format to another

## Known limitations ##
  * <b>Application must be restarted after every recording</b> (bug). Otherwise all the material recorded during a single session will not render
  * Windows/Linux taskbar is visible on the video
  * Mouse pointer is not visible
  * Pause feature is not implemented yet
  * Video colors might look different than on some monitor screens (might have to do with monitor color settings)

## TODO list ##
  * Watch recorded video from app
  * Ability to pause recording
  * <b>Fix audio</b>
  * Audio synchronization
  * Add more audio/video formats/codecs
  * Web application
  * Support audio sources other than microphone.
  * Framerate setting
  * Webcam support

## Libraries used ##
  * Xuggler
  * Packet Multibroadcaster
  * FFmpeg