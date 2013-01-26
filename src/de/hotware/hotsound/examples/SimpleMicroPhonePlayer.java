package de.hotware.hotsound.examples;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

import de.hotware.hotsound.audio.data.BasicPlaybackAudioDevice;
import de.hotware.hotsound.audio.data.RecordAudio;
import de.hotware.hotsound.audio.player.MusicEndEvent;
import de.hotware.hotsound.audio.player.MusicExceptionEvent;
import de.hotware.hotsound.audio.player.MusicListener;
import de.hotware.hotsound.audio.player.MusicPlayer;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.RecordSong;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;


public class SimpleMicroPhonePlayer {
	
	public static void main(String[] args) throws MusicPlayerException, MalformedURLException, InterruptedException, LineUnavailableException {
			List<Mixer> mixers = RecordAudio.getRecordMixers();
			if(mixers.size() > 0) {
				ExecutorService service = Executors.newSingleThreadExecutor();
				final MusicPlayer player = new StreamMusicPlayer(new MusicListener() {

					@Override
					public void onEnd(MusicEndEvent pEvent) {
						System.out.println("stopped");
						try {
							pEvent.getSource().close();
						} catch(MusicPlayerException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onException(MusicExceptionEvent pEvent) {
						System.out.println(pEvent.getException());
					}
					
				}, service);
				Mixer mixer = mixers.get(0);
				mixer.open();
				player.insert(new RecordSong(mixer), new BasicPlaybackAudioDevice());
				player.start();
				//wait 10 seconds (equals approx. 10 seconds of saved audio)
				Thread.sleep(10000);
				player.stop();
				service.shutdown();
				service.awaitTermination(1000, TimeUnit.MILLISECONDS);
				player.close();
			}
	}

}
