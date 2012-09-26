package de.hotware.hotsound.examples;

import java.io.File;
import java.io.IOException;

import de.hotware.hotsound.audio.player.BasicSong;
import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.IMusicPlayer.SongInsertionException;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;

/**
 * Player that plays on the command line and it's 27 lines long
 * 
 * @author Martin Braun
 */
public class SimplePlayer {

	public static void main(String[] args) throws SongInsertionException,
			IOException {
		if(args.length >= 1) {
			IMusicPlayer player = new StreamMusicPlayer();
			player.insert(new BasicSong(new File(args[0])));
			player.startPlayback();
		}
	}

}