package de.hotware.hotsound.examples;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

import de.hotware.hotsound.audio.data.BasicAudioDevice;
import de.hotware.hotsound.audio.data.RecordAudio;
import de.hotware.hotsound.audio.player.IMusicListener;
import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.RecordSong;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;


public class SimpleMicroPhonePlayer {
	
	public static void main(String[] args) throws MusicPlayerException, MalformedURLException, InterruptedException, LineUnavailableException {
			List<Mixer> mixers = RecordAudio.getRecordMixers();
			if(mixers.size() > 0) {
				ExecutorService service = Executors.newSingleThreadExecutor();
				IMusicPlayer player = new StreamMusicPlayer(new IMusicListener() {

					@Override
					public void onEnd(MusicEvent pEvent) {
						System.out.println(pEvent.getThrowable());
						System.out.println("stopped");
					}
					
				}, service);
				Mixer mixer = mixers.get(0);
				mixer.open();
				player.insert(new RecordSong(mixer), new BasicAudioDevice());
				player.start();
				//wait 10 seconds (equals approx. 10 seconds of saved audio)
				Thread.sleep(10000);
				player.stop();
				service.shutdown();
				service.awaitTermination(1000, TimeUnit.MILLISECONDS);
			}
	}

}
