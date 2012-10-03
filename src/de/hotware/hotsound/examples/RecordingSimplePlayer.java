package de.hotware.hotsound.examples;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

import de.hotware.hotsound.audio.player.IMusicListener;
import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.MusicEndEvent;
import de.hotware.hotsound.audio.player.MusicExceptionEvent;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.RecordSong;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;
import de.hotware.hotsound.audio.data.IAudioDevice;
import de.hotware.hotsound.audio.data.IAudioDevice.AudioDeviceException;
import de.hotware.hotsound.audio.data.RecordAudio;
import de.hotware.hotsound.audio.data.RecordingAudioDevice;

public class RecordingSimplePlayer {

	public static void main(String[] args) throws MusicPlayerException,
			MalformedURLException,
			InterruptedException, LineUnavailableException {
		List<Mixer> mixers = RecordAudio.getRecordMixers();
		if(mixers.size() > 0) {
			final IAudioDevice dev = new RecordingAudioDevice(new File("recording.wav"));
			IMusicPlayer player = new StreamMusicPlayer(new IMusicListener() {

				@Override
				public void onEnd(MusicEndEvent pEvent) {
					System.out.println("stopped");
					try {
						pEvent.getSource().close();
					} catch(MusicPlayerException e) {
						e.printStackTrace();
					}
					try {
						dev.close();
					} catch(AudioDeviceException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onException(MusicExceptionEvent pEvent) {
					System.out.println(pEvent.getException());
				}
				
			});
			Mixer mixer = mixers.get(0);
			player.insert(new RecordSong(mixer), dev);
			player.start();
			Thread.sleep(10000);
			player.stop();
		}
	}

}
