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

        assertTrue(ss.post(song1) == 0);
        assertTrue(ss.post(song2) == 1);
        assertTrue(ss.post(song2) == 2);
    }

    @Test
    public void putSong() throws Exception{
        SongMemoryService ss = new SongMemoryService();

        ss.post(song1);
        assertTrue(ss.get(0) == song1);

        ss.put(song2, 0);
        assertTrue(ss.get(0) == song2);
    }

    @Test
    public void getSong() throws Exception {
        SongMemoryService ss = new SongMemoryService();
        ss.post(song1);

        assertNotNull(ss.get(0));
        assertEquals(ss.get(0), song1);

        ss.post(song2);
        ss.post(song1);

        assertEquals(ss.get(2), song1);
        assertEquals(ss.get(1), song2);
    }

    @Test
    public void getSongList() throws Exception {
        SongMemoryService ss = new SongMemoryService();
        ss.post(song1);
        ss.post(song1);
        ss.post(song2);
        ss.post(song2);

        List<Song> songDB = ss.getList();

        assertEquals(songDB.get(0), song1);
        assertEquals(songDB.get(3), song2);

        exception.expect(IndexOutOfBoundsException.class);
        songDB.get(4);
    }

    @Test
    public void deleteSong() throws Exception{
        SongMemoryService ss = new SongMemoryService();
        ss.post(song1);
        ss.post(song2);
        ss.post(song1);
        ss.post(song2);

        ss.delete(0);
        assertNull(ss.get(0));

        ss.delete(3);
        assertNull(ss.get(3));

        ss.post(song1);
        assertEquals(ss.get(4), song1);

        ss.delete(4);
        assertNull(ss.get(4));
    }
}