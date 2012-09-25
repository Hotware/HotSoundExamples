package de.hotware.hotsound.examples;

import java.io.File;
import java.net.MalformedURLException;

import de.hotware.hotsound.audio.player.BasicSong;
import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.IMusicPlayer.SongInsertionException;
import de.hotware.hotsound.audio.player.IPlaybackListener;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;

/**
 * Player that plays on the command line and it's 37 lines long
 * 
 * @author Martin Braun
 */
public class SimplePlayer {

	public static void main(String[] args) throws MalformedURLException,
			SongInsertionException {
		if(args.length >= 1) {
			IMusicPlayer player = new StreamMusicPlayer(new IPlaybackListener() {

				@Override
				public void onEnd(PlaybackEndEvent pEvent) {
					System.out.println("Playback ended");
					System.exit(1);
				}

			});
			player.insert(new BasicSong(new File(args[0])));
			player.startPlayback();
		}
	}

}
