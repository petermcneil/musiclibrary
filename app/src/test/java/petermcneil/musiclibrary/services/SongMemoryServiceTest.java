package petermcneil.musiclibrary.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.memory.SongMemoryService;

import java.util.List;

import static org.junit.Assert.*;

public class SongMemoryServiceTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    Song song1 = Song.songBuilder().title("Song 1").build();
    Song song2 = Song.songBuilder().title("Song 2").build();

    @Test
    public void postSong() throws Exception{
        SongMemoryService ss = new SongMemoryService();

        assertTrue(ss.postSong(song1) == 0);
        assertTrue(ss.postSong(song2) == 1);
        assertTrue(ss.postSong(song2) == 2);
    }

    @Test
    public void putSong() throws Exception{
        SongMemoryService ss = new SongMemoryService();

        ss.postSong(song1);
        assertTrue(ss.getSong(0) == song1);

        ss.putSong(song2, 0);
        assertTrue(ss.getSong(0) == song2);
    }

    @Test
    public void getSong() throws Exception {
        SongMemoryService ss = new SongMemoryService();
        ss.postSong(song1);

        assertNotNull(ss.getSong(0));
        assertEquals(ss.getSong(0), song1);

        ss.postSong(song2);
        ss.postSong(song1);

        assertEquals(ss.getSong(2), song1);
        assertEquals(ss.getSong(1), song2);
    }

    @Test
    public void getSongList() throws Exception {
        SongMemoryService ss = new SongMemoryService();
        ss.postSong(song1);
        ss.postSong(song1);
        ss.postSong(song2);
        ss.postSong(song2);

        List<Song> songDB = ss.getSongList();

        assertEquals(songDB.get(0), song1);
        assertEquals(songDB.get(3), song2);

        exception.expect(IndexOutOfBoundsException.class);
        songDB.get(4);
    }

    @Test
    public void deleteSong() throws Exception{
        SongMemoryService ss = new SongMemoryService();
        ss.postSong(song1);
        ss.postSong(song2);
        ss.postSong(song1);
        ss.postSong(song2);

        ss.deleteSong(0);
        assertNull(ss.getSong(0));

        ss.deleteSong(3);
        assertNull(ss.getSong(3));

        ss.postSong(song1);
        assertEquals(ss.getSong(4), song1);

        ss.deleteSong(4);
        assertNull(ss.getSong(4));
    }
}