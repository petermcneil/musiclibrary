package petermcneil.musiclibrary.services;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import petermcneil.domain.Playlist;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.memory.PlaylistMemoryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlaylistMemoryServiceTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    Song song1 = Song.songBuilder().title("Song 1").build();
    Song song2 = Song.songBuilder().title("Song 2").build();

    Playlist playlist1 = Playlist.playlistBuilder().title("playlist1").tracks(ImmutableList.of(song1, song2)).build();
    Playlist playlist2 = Playlist.playlistBuilder().title("playlist2").tracks(ImmutableList.of(song2, song2, song1, song2)).build();
    Playlist playlist3 = Playlist.playlistBuilder().title("playlist3").tracks(ImmutableList.of(song1, song2, song2, song2)).build();

    @Test
    public void getPlaylistList() throws Exception {
        PlaylistMemoryService ps = new PlaylistMemoryService();

        ps.post(playlist2);
        ps.post(playlist1);
        ps.post(playlist1);
        ps.post(playlist3);

        List playlists = new ArrayList(ps.getList());

        assertEquals(playlist1, playlists.get(1));
        assertEquals(playlist2, playlists.get(0));
//        assertEquals(playlist3, playlists.get(2));

    }

    @Test
    public void getPlaylist() throws Exception {
        PlaylistMemoryService ps = new PlaylistMemoryService();

        ps.post(playlist1);
        ps.post(playlist2);
        ps.post(playlist3);

        assertTrue(ps.get(0) == playlist1);
        assertTrue(ps.get(2) == playlist3);
    }

    @Test
    public void postPlaylist() throws Exception {
        PlaylistMemoryService ps = new PlaylistMemoryService();

        assertTrue(ps.post(playlist1) == 0);
        assertTrue(ps.post(playlist1) == 1);
    }

    @Test
    public void putPlaylist() throws Exception {
        PlaylistMemoryService ps = new PlaylistMemoryService();

        ps.post(playlist1);
        assertTrue(ps.get(0) == playlist1);

        ps.put(playlist2, 0);
        assertTrue(ps.get(0) == playlist2);
    }

    @Test
    public void deletePlaylist() throws Exception {
        PlaylistMemoryService ps = new PlaylistMemoryService();

        ps.post(playlist1);
        ps.post(playlist2);
        ps.post(playlist3);

        ps.delete(0);
        assertNull(ps.get(0));
        assertEquals(ps.getList().size(), 2);

        ps.delete(2);
        assertNull(ps.get(2));

        assertTrue(ps.get(1) == playlist2);
    }

}