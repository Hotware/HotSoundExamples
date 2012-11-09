/**
 * File PlaylistPlayer.java
 * ---------------------------------------------------------
 *
 * Copyright (C) 2012 Martin Braun (martinbraun123@aol.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * - The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * - The origin of the software must not be misrepresented.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * TL;DR: As long as you clearly give me credit for this Software, you are free to use as you like, even in commercial software, but don't blame me
 *   if it breaks something.
 */
package de.hotware.hotsound.examples;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hotware.hotsound.audio.player.MusicListener;
import de.hotware.hotsound.audio.player.MusicPlayer;
import de.hotware.hotsound.audio.player.Song;
import de.hotware.hotsound.audio.player.MusicEndEvent;
import de.hotware.hotsound.audio.player.MusicExceptionEvent;
import de.hotware.hotsound.audio.player.MusicPlayerException;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;
import de.hotware.hotsound.audio.playlist.PlaylistParser;
import de.hotware.hotsound.audio.playlist.StockParser;

public class PlaylistPlayer {
	
	protected int mCurrent;
	protected List<Song> mPlaylist;
	protected MusicPlayer mMusicPlayer;
	
	public PlaylistPlayer() {
		this.mCurrent = 0;
		this.mMusicPlayer = new StreamMusicPlayer(new MusicListener() {

			@Override
			public void onEnd(MusicEndEvent pEvent) {
				System.out.println(pEvent.getSource() + " ended.");
				int size = PlaylistPlayer.this.mPlaylist.size();
				int current = ++PlaylistPlayer.this.mCurrent;
				try {
					PlaylistPlayer.this.mMusicPlayer.insert(PlaylistPlayer.this.mPlaylist.get(current % size));
					PlaylistPlayer.this.mMusicPlayer.start();
					System.out.println(pEvent.getSource() + " started again.");
				} catch (MusicPlayerException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onException(MusicExceptionEvent pEvent) {
				pEvent.getException().printStackTrace();
			}
			
		});
	}
	
	public void init(File pFile) throws IOException, MusicPlayerException {
		PlaylistParser parser = StockParser.M3U;
		final List<Song> playlist = parser.parse(pFile);
		if(playlist.size() > 0 ) {
			this.mPlaylist = playlist;
			this.mMusicPlayer.insert(playlist.get(0));
		}
	}
	
	public void start() throws MusicPlayerException {
		this.mMusicPlayer.start();
		System.out.println(this.mMusicPlayer + " started.");
	}
	
	public static void main(String[] pArgs) throws MusicPlayerException, InterruptedException, IOException {
		PlaylistPlayer player = new PlaylistPlayer();
		player.init(new File(pArgs[0]));
		player.start();
	}
	
}