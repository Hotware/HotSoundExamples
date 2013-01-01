package de.hotware.hotsound.examples;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hotware.hotsound.audio.data.AudioDevice;
import de.hotware.hotsound.audio.data.BasicPlaybackAudioDevice;
import de.hotware.hotsound.audio.data.MultiAudioDevice;
import de.hotware.hotsound.audio.data.RecordingAudioDevice;
import de.hotware.hotsound.audio.player.MusicPlayer;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.SavingSong;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;


public class SavingSimplePlayer {
	
	public static void main(String[] args) throws MusicPlayerException, MalformedURLException, InterruptedException {
		if(args.length >= 0) {
			List<AudioDevice> audioDevices = new ArrayList<>();
			audioDevices.add(new BasicPlaybackAudioDevice());
			audioDevices.add(new RecordingAudioDevice(new File("saving.wav")));
			final AudioDevice dev = new MultiAudioDevice(audioDevices);
			MusicPlayer player = new StreamMusicPlayer();
			player.insert(new SavingSong(new URL("http://listen.technobase.fm/tunein-oggvorbis-pls.ogg")), dev);
			player.start();
			//wait 10 seconds (equals approx. 10 seconds of saved audio)
			//always stop for bug avoidance in saving the audiofile
			Thread.sleep(10000);
			player.stop();
		}
	}

}