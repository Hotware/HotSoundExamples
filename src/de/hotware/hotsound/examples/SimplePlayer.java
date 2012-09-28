package de.hotware.hotsound.examples;

import java.io.File;
import java.net.MalformedURLException;

import de.hotware.hotsound.audio.player.BasicSong;
import de.hotware.hotsound.audio.player.IMusicListener;
import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;

/**
 * 
 * @author Martin Braun
 */
public class SimplePlayer {

	public static void main(String[] args) throws MusicPlayerException, MalformedURLException {
		if(args.length >= 1) {
			IMusicPlayer player = new StreamMusicPlayer(new IMusicListener() {

				@Override
				public void onEnd(MusicEvent pEvent) {
					System.out.println(pEvent.getThrowable());
					System.out.println("stopped");
					try {
						pEvent.getSource().close();
					} catch(MusicPlayerException e) {
						e.printStackTrace();
					}
				}
				
			});
			player.insert(new BasicSong(new File(args[0])));
			player.start();
		}
	}

}