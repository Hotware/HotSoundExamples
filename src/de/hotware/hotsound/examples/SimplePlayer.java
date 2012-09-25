package de.hotware.hotsound.examples;

import java.io.File;
import java.net.MalformedURLException;

import de.hotware.hotsound.audio.player.BasicSong;
import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.IMusicPlayer.SongInsertionException;
import de.hotware.hotsound.audio.player.IPlaybackListener;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;


public class SimplePlayer {
	
	public static void main(String[] args) throws MalformedURLException, SongInsertionException {
		if(args.length > 1) {
			IMusicPlayer player = new StreamMusicPlayer(new IPlaybackListener() {
	
				@Override
				public void onEnd(PlaybackEndEvent pEvent) {
					System.out.println("Playback ended");
				}
				
			});
			player.insert(new BasicSong(new File(args[0])));
			player.startPlayback();
		}
	}

}
