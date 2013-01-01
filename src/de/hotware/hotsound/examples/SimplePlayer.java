package de.hotware.hotsound.examples;

import java.io.File;
import java.net.MalformedURLException;

import de.hotware.hotsound.audio.player.BasicPlaybackSong;
import de.hotware.hotsound.audio.player.MusicEndEvent;
import de.hotware.hotsound.audio.player.MusicExceptionEvent;
import de.hotware.hotsound.audio.player.MusicListener;
import de.hotware.hotsound.audio.player.MusicPlayer;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;

/**
 * 
 * @author Martin Braun
 */
public class SimplePlayer {

	public static void main(String[] args) throws MusicPlayerException, MalformedURLException {
		if(args.length >= 1) {
			MusicPlayer player = new StreamMusicPlayer(new MusicListener() {

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
				
			});
			player.insert(new BasicPlaybackSong(new File(args[0])));
			player.start();
//			player.skip(100000);
		}
	}

}