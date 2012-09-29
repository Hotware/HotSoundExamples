package de.hotware.hotsound.examples;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.hotware.hotsound.audio.data.BasicAudioDevice;
import de.hotware.hotsound.audio.data.IAudioDevice;
import de.hotware.hotsound.audio.data.MultiAudioDevice;
import de.hotware.hotsound.audio.data.RecordingAudioDevice;
import de.hotware.hotsound.audio.player.IMusicListener;
import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.SavingSong;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;


public class SavingSimplePlayer {
	
	public static void main(String[] args) throws MusicPlayerException, MalformedURLException, InterruptedException {
		if(args.length >= 1) {
			ExecutorService service = Executors.newSingleThreadExecutor();
			//multithreading because of blocking behaviour
			IMusicPlayer player = new StreamMusicPlayer(new IMusicListener() {

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
				public void onExeption(MusicExceptionEvent pEvent) {
					System.out.println(pEvent.getException());
				}
				
			}, service);
			List<IAudioDevice> audioDevices = new ArrayList<>();
			//playback
			audioDevices.add(new BasicAudioDevice());
			audioDevices.add(new RecordingAudioDevice(new File("saving.wav")));
			player.insert(new SavingSong(new URL(args[0])), new MultiAudioDevice(audioDevices));
			player.start();
			//wait 10 seconds (equals approx. 10 seconds of saved audio)
			Thread.sleep(10000);
			//always stop for bug avoidance in saving the audiofile
			player.stop();
			service.shutdown();
			service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		}
	}

}