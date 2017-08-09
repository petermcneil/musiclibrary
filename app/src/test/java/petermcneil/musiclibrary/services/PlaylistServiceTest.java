package petermcneil.musiclibrary.services;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import petermcneil.domain.Playlist;
import petermcneil.domain.Song;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class PlaylistServiceTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    Song song1 = Song.songBuilder().title("Song 1").build();
    Song song2 = Song.songBuilder().title("Song 2").build();

    Playlist playlist1 = Playlist.playlistBuilder().title("playlist1").tracks(ImmutableList.of(song1, song2)).build();
    Playlist playlist2 = Playlist.playlistBuilder().title("playlist2").tracks(ImmutableList.of(song2, song2, song1, song2)).build();
    Playlist playlist3 = Playlist.playlistBuilder().title("playlist3").tracks(ImmutableList.of(song1, song2, song2, song2)).build();

    @Test
    public void getPlaylistList() throws Exception {
        PlaylistService ps = new PlaylistService();

        ps.postPlaylist(playlist2);
        ps.postPlaylist(playlist1);
        ps.postPlaylist(playlist1);
        ps.postPlaylist(playlist3);

        List playlists = new ArrayList(ps.getPlaylistList());

        assertEquals(playlist1, playlists.get(1));
        assertEquals(playlist2, playlists.get(0));
        assertEquals(playlist3, playlists.get(2));

    }

    @Test
    public void getPlaylist() throws Exception {
        PlaylistService ps = new PlaylistService();

        ps.postPlaylist(playlist1);
        ps.postPlaylist(playlist2);
        ps.postPlaylist(playlist3);

        assertTrue(ps.getPlaylist(0) == playlist1);
        assertTrue(ps.getPlaylist(2) == playlist3);
    }

    @Test
    public void postPlaylist() throws Exception {
        PlaylistService ps = new PlaylistService();

        assertTrue(ps.postPlaylist(playlist1) == 0);
        assertTrue(ps.postPlaylist(playlist1) == 1);
    }

    @Test
    public void putPlaylist() throws Exception {
        PlaylistService ps = new PlaylistService();

        ps.postPlaylist(playlist1);
        assertTrue(ps.getPlaylist(0) == playlist1);

        ps.putPlaylist(playlist2, 0);
        assertTrue(ps.getPlaylist(0) == playlist2);
    }

    @Test
    public void deletePlaylist() throws Exception {
        PlaylistService ps = new PlaylistService();

        ps.postPlaylist(playlist1);
        ps.postPlaylist(playlist2);
        ps.postPlaylist(playlist3);

        ps.deletePlaylist(0);
        assertNull(ps.getPlaylist(0));
        assertEquals(ps.getPlaylistList().size(), 2);

        ps.deletePlaylist(2);
        assertNull(ps.getPlaylist(2));

        assertTrue(ps.getPlaylist(1) == playlist2);
    }

}