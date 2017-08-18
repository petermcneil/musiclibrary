package petermcneil.musiclibrary.services;


import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import petermcneil.domain.Artist;
import petermcneil.domain.Recording;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.memory.ArtistMemoryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ArtistMemoryServiceTest {
    Song song1 = Song.songBuilder().title("Song 1").build();
    Song song2 = Song.songBuilder().title("Song 2").build();

    Recording recording1 = Recording.recordingBuilder().tracks(ImmutableSet.of(song1, song2)).build();
    Recording recording2 = Recording.recordingBuilder().tracks(ImmutableSet.of(song1)).build();
    Recording recording3 = Recording.recordingBuilder().tracks(ImmutableSet.of(song1, song2, song2)).build();
    Recording recording4 = Recording.recordingBuilder().tracks(ImmutableSet.of(song1, song1, song2, song1)).build();

    Artist artist1 = Artist.artistBuilder().name("artist1").build();
    Artist artist2 = Artist.artistBuilder().name("artist2").build();
    Artist artist3 = Artist.artistBuilder().name("artist3").build();

    @Test
    public void get() throws Exception {
        ArtistMemoryService as = new ArtistMemoryService();

        as.post(artist1);
        assertNotNull(as.get(0));

        as.post(artist2);
        as.post(artist3);
        as.post(artist2);

        assertTrue(as.get(0) == artist1);
        assertTrue(as.get(2) == artist3);
    }

    @Test
    public void getList() throws Exception {
        ArtistMemoryService as = new ArtistMemoryService();

        as.post(artist1);
        as.post(artist2);
        as.post(artist3);
        as.post(artist1);
        as.post(artist2);
        as.post(artist3);

        List artistList = new ArrayList(as.getList());

        assertEquals(artist1, artistList.get(0));
        assertEquals(artist2, artistList.get(1));
        assertEquals(artist3, artistList.get(5));
    }

    @Test
    public void post() throws Exception {
        ArtistMemoryService as = new ArtistMemoryService();

        assertTrue(as.post(artist1) == 0);
        assertTrue(as.post(artist1) == 1);
        assertTrue(as.post(artist1) == 2);
        assertTrue(as.post(artist1) == 3);
    }

    @Test
    public void put() throws Exception {
        ArtistMemoryService as = new ArtistMemoryService();

        as.post(artist1);
        as.post(artist2);

        assertEquals(as.get(0), artist1);
        assertEquals(as.get(1), artist2);

        as.put(artist2, 0);
        as.put(artist1, 1);

        assertEquals(as.get(0), artist2);
        assertEquals(as.get(1), artist1);
    }

    @Test
    public void deleteArtist() throws Exception {
        ArtistMemoryService as = new ArtistMemoryService();

        as.post(artist1);
        as.post(artist2);

        assertEquals(as.get(0), artist1);
        assertEquals(as.get(1), artist2);

        as.delete(0);

        assertNull(as.get(0));
    }

}