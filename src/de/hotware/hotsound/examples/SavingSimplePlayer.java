package de.hotware.hotsound.examples;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.hotware.hotsound.audio.data.SavingAudioDevice;
import de.hotware.hotsound.audio.player.BasicSong;
import de.hotware.hotsound.audio.player.IMusicListener;
import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;


public class SavingSimplePlayer {
	
	public static void main(String[] args) throws MusicPlayerException, MalformedURLException, InterruptedException {
		if(args.length >= 2) {
			ExecutorService service = Executors.newSingleThreadExecutor();
			//multithreading because of blocking behaviour
			IMusicPlayer player = new StreamMusicPlayer(new IMusicListener() {

				@Override
				public void onEnd(MusicEvent pEvent) {
					System.out.println("stopped");
				}
				
			}, service);
			player.insert(new BasicSong(new URL(args[0])), new SavingAudioDevice(new File(args[1])));
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